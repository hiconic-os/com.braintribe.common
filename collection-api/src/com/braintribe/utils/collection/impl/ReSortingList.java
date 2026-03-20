package com.braintribe.utils.collection.impl;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * A list that allows to change the order of its elements by keys via {@link #sort(Object...)} method.
 * 
 * @author peter.gazdik
 */
public class ReSortingList<E, K> implements Iterable<E> {

	private volatile List<E> list; // cache for #toList(), read-only, never modified
	private volatile List<Entry> entries = emptyList(); // read-only, never modified
	private final Map<K, Entry> s_map = new HashMap<>(); // only accessed from synced methods

	public synchronized void add(K key, E element) {
		Objects.requireNonNull(key, "Key must not be null");
		Objects.requireNonNull(element, "Element must not be null");

		if (s_map.containsKey(key))
			throw new IllegalArgumentException("Duplicate key: " + key);

		Entry newEntry = new Entry(key, element);

		List<Entry> newEntries = new ArrayList<>(entries);
		newEntries.add(newEntry);

		s_setNewEntries(newEntries);
		s_map.put(key, newEntry);
	}

	/**
	 * Rearranges the elements by their corresponding keys, only moving elements as little as possible towards lower positions.
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * array: ["A", "B", "C", "D", "E", "F", "G"]
	 * sort("B", "D", "G", "C")
	 * result: [ "B", "A", "D", "G", "C", "E", "F"]
	 * </pre>
	 **/
	public synchronized void sort(K... keys) {
		if (keys == null || keys.length < 2)
			return;

		Map<K, Integer> keyToIndex = new HashMap<>();
		for (int i = 0; i < keys.length; i++)
			if (keyToIndex.put(keys[i], i) != null)
				throw new IllegalArgumentException("Duplicate key: " + keys[i]);

		List<Entry> newEntries = new ArrayList<>(entries.size());
		Set<K> insertedKeys = new HashSet<>();
		int ix = 0;
		for (Entry entry : entries) {
			if (!insertedKeys.add(entry.key))
				continue;

			// We simply re-insert entries in original order, with one exception
			// if an entry occurs which we are sorting (key is in keys array), we insert all entries for earlier keys in front of it

			// entry is not being sorted, just insert it
			if (!keyToIndex.containsKey(entry.key)) {
				newEntries.add(entry);
				insertedKeys.add(entry.key);
				continue;
			}

			int pos = keyToIndex.get(entry.key);

			// entry is being sorted, so insert entries for all smaller keys and the entry
			while (ix <= pos) {
				K sortedKey = keys[ix++];

				Entry sortedEntry = s_map.get(sortedKey);
				if (sortedEntry == null)
					// ignore keys with no value
					continue;

				newEntries.add(sortedEntry);
				insertedKeys.add(sortedKey);
			}
		}

		s_setNewEntries(newEntries);
	}

	private void s_setNewEntries(List<Entry> newEntries) {
		list = null;
		entries = newEntries;
	}

	@Override
	public Iterator<E> iterator() {
		return toList().iterator();
	}

	public List<E> toList() {
		List<E> elements = list;
		if (elements != null)
			return elements;

		synchronized (this) {
			elements = list;
			if (elements != null)
				return list;

			List<Entry> currentEntries = entries;
			return list = Collections.unmodifiableList(currentEntries.stream().map(e -> e.value).collect(Collectors.toList()));
		}
	}

	private class Entry {
		public final K key;
		public final E value;

		public Entry(K key, E value) {
			this.key = key;
			this.value = value;
		}

	}
}
