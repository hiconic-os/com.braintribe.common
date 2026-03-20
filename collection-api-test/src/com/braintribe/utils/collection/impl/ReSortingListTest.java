package com.braintribe.utils.collection.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * Tests for {@link ReSortingList}
 */
public class ReSortingListTest {

	private final ReSortingList<String, String> list = new ReSortingList<>();

	// ######################################
	// ## . . . . . Empty list . . . . . . ##
	// ######################################

	@Test
	public void testEmptyList() {
		assertThat(list.toList()).isEmpty();
		assertThat(list.iterator().hasNext()).isFalse();
	}

	// ######################################
	// ## . . . . . . . Add . . . . . . . .##
	// ######################################

	@Test
	public void addMultipleElements() {
		list.add("k1", "v1");
		list.add("k2", "v2");
		list.add("k3", "v3");

		assertThat(list.toList()).containsExactly("v1", "v2", "v3");
	}

	@Test(expected = NullPointerException.class)
	public void addNullKeyThrowsNpe() {
		list.add(null, "v1");
	}

	@Test(expected = NullPointerException.class)
	public void addNullElementThrowsNpe() {
		list.add("k1", null);
	}

	// ######################################
	// ## . . . . . . . Sort . . . . . . . ##
	// ######################################

	@Test
	public void sortWithZeorOrOneKeyDoesNothing() {
		list.add("k1", "v1");
		list.add("k2", "v2");

		List<String> l1 = list.toList();
		list.sort();
		list.sort("k1");

		assertThat(l1).containsExactly("v1", "v2");
		assertThat(l1).isSameAs(list.toList());
	}

	@Test
	public void sortReordersElements() {
		list.add("k1", "v1");
		list.add("k2", "v2");
		list.add("k3", "v3");

		list.sort("k3", "k1", "k2");

		assertThat(list.toList()).containsExactly("v3", "v1", "v2");
	}

	@Test
	public void sortWithSubsetOfKeys() {
		list.add("k1", "v1");
		list.add("k2", "v2");
		list.add("k3", "v3");
		list.add("k4", "v4");

		// Only sort k1 and k3, swapping their positions
		list.sort("k3", "k1");

		// k2 and k4 stay in place; k1 and k3 get swapped in their original positions (index 0 and 2)
		assertThat(list.toList()).containsExactly("v3", "v1", "v2", "v4");
	}

	@Test
	public void sortWithNonExistentKeysIgnoresThem() {
		list.add("k1", "v1");
		list.add("k2", "v2");

		list.sort("k2", "nonexistent", "k1");

		assertThat(list.toList()).containsExactly("v2", "v1");
	}

	// ######################################
	// ## . . . . . . toList . . . . . . . ##
	// ######################################

	@Test
	public void toListReturnsCachedInstance() {
		list.add("k1", "v1");

		List<String> first = list.toList();
		List<String> second = list.toList();

		assertThat(first).isSameAs(second);
	}

	@Test
	public void toListCacheInvalidatedByAdd() {
		list.add("k1", "v1");

		List<String> before = list.toList();

		list.add("k2", "v2");

		List<String> after = list.toList();

		assertThat(before).isNotSameAs(after);
		assertThat(after).containsExactly("v1", "v2");
	}

	@Test
	public void toListCacheInvalidatedBySort() {
		list.add("k1", "v1");
		list.add("k2", "v2");

		List<String> before = list.toList();

		list.sort("k2", "k1");

		List<String> after = list.toList();

		assertThat(before).isNotSameAs(after);
		assertThat(after).containsExactly("v2", "v1");
	}

	// ######################################
	// ## . . . . . . Iterator. . . . . . .##
	// ######################################

	@Test
	public void iteratorReturnsAllElements() {
		list.add("k1", "v1");
		list.add("k2", "v2");
		list.add("k3", "v3");

		List<String> collected = new ArrayList<>();
		for (String e : list)
			collected.add(e);

		assertThat(collected).containsExactly("v1", "v2", "v3");
	}

	@Test
	public void iteratorOnEmptyListThrowsNoSuchElement() {
		Iterator<String> it = list.iterator();

		assertThatThrownBy(it::next).isInstanceOf(NoSuchElementException.class);
	}

	// ######################################
	// ## . . Sort edge cases. . . . . . . ##
	// ######################################

	@Test
	public void sortOnEmptyListDoesNothing() {
		list.sort("a", "b");

		assertThat(list.toList()).isEmpty();
	}

	@Test
	public void sortSubsetLeavesUnsortedElementsInPlace() {
		list.add("a", "A");
		list.add("b", "B");
		list.add("c", "C");
		list.add("d", "D");
		list.add("e", "E");

		// Only sort a, c, e — they occupy indices 0, 2, 4
		list.sort("e", "c", "a");

		// Unsorted elements b, d remain at indices 1, 3
		assertThat(list.toList()).containsExactly("E", "C", "A", "B", "D");
	}

	@Test(expected = IllegalArgumentException.class)
	public void sortWithDuplicateKeysInSortArray() {
		list.add("a", "A");
		list.add("b", "B");
		list.add("c", "C");

		// k1 appears twice in the sort array — it maps to the same element via the map
		list.sort("a", "c", "c", "b");
	}

	// ######################################
	// ## . . . . toList extras . . . . . .##
	// ######################################

	@Test
	public void toListOnEmptyListReturnsCachedEmptyList() {
		List<String> first = list.toList();
		List<String> second = list.toList();

		assertThat(first).isEmpty();
		assertThat(first).isSameAs(second);
	}

	@Test
	public void toListIsImmutable() {
		list.add("k1", "v1");
		list.add("k2", "v2");

		List<String> result = list.toList();

		assertThatThrownBy(() -> result.add("v3")).isInstanceOf(UnsupportedOperationException.class);
	}

	@Test
	public void toListCacheInvalidatedByAddThenSort() {
		list.add("k1", "v1");
		list.add("k2", "v2");

		List<String> ref1 = list.toList();

		list.add("k3", "v3");
		List<String> ref2 = list.toList();

		list.sort("k3", "k2", "k1");
		List<String> ref3 = list.toList();

		assertThat(ref1).isNotSameAs(ref2);
		assertThat(ref2).isNotSameAs(ref3);
		assertThat(ref3).containsExactly("v3", "v2", "v1");
	}

	// ######################################
	// ## . . . . Large list . . . . . . . ##
	// ######################################

	@Test
	public void addAndSortManyElements() {
		int count = 100;
		for (int i = 0; i < count; i++)
			list.add("k" + i, "v" + i);

		assertThat(list.toList()).hasSize(count);
		assertThat(list.toList().get(0)).isEqualTo("v0");
		assertThat(list.toList().get(count - 1)).isEqualTo("v" + (count - 1));

		// Reverse the first 5 elements
		list.sort("k4", "k3", "k2", "k1", "k0");

		assertThat(list.toList().get(0)).isEqualTo("v4");
		assertThat(list.toList().get(1)).isEqualTo("v3");
		assertThat(list.toList().get(2)).isEqualTo("v2");
		assertThat(list.toList().get(3)).isEqualTo("v1");
		assertThat(list.toList().get(4)).isEqualTo("v0");
		// Element at index 5 should be unchanged
		assertThat(list.toList().get(5)).isEqualTo("v5");
	}

	// ######################################
	// ## . . . . Concurrency . . . . . . .##
	// ######################################

	@Test
	public void concurrentAddsDoNotLoseElements() throws Exception {
		int threadCount = 10;
		int elementsPerThread = 50;
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int t = 0; t < threadCount; t++) {
			int threadId = t;
			executor.submit(() -> {
				try {
					for (int i = 0; i < elementsPerThread; i++)
						list.add("t" + threadId + "-k" + i, "t" + threadId + "-v" + i);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await(10, TimeUnit.SECONDS);
		executor.shutdown();

		// Each add prepends, so total entries = threadCount * elementsPerThread
		assertThat(list.toList()).hasSize(threadCount * elementsPerThread);
	}

	@Test
	public void concurrentSortAndAddDoNotThrow() throws Exception {
		// Pre-populate
		for (int i = 0; i < 20; i++)
			list.add("k" + i, "v" + i);

		int threadCount = 4;
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		CountDownLatch latch = new CountDownLatch(threadCount);

		// Two threads adding, two threads sorting
		for (int t = 0; t < threadCount; t++) {
			int threadId = t;
			executor.submit(() -> {
				try {
					for (int i = 0; i < 100; i++) {
						if (threadId % 2 == 0)
							list.add("new-k" + threadId + "-" + i, "new-v" + threadId + "-" + i);
						else
							list.sort("k0", "k1");
					}
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await(10, TimeUnit.SECONDS);
		executor.shutdown();

		// Just verify it doesn't throw and returns a non-null, non-empty list
		assertThat(list.toList()).isNotEmpty();
	}
}