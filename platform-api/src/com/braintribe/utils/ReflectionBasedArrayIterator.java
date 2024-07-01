// ============================================================================
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ============================================================================
// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
// 
// This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
// 
// This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License along with this library; See http://www.gnu.org/licenses/.
// ============================================================================
package com.braintribe.utils;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.braintribe.utils.lcd.CommonTools;

/**
 * Reflection-based array iterator (i.e. works with any type of array).
 *
 * @author michael.lafite
 */
public class ReflectionBasedArrayIterator implements Iterator<Object> {

	private final Object array;

	private int index = 0;

	/**
	 * Creates a new iterator instance. The passed argument must be an array!
	 */
	public ReflectionBasedArrayIterator(final Object array) {
		if (!array.getClass().isArray()) {
			throw new IllegalArgumentException(
					"The passed object is not an array! " + CommonTools.getParametersString("array", array, "class", array.getClass().getName()));
		}
		this.array = array;
	}

	@Override
	public boolean hasNext() {
		return this.index < Array.getLength(this.array);
	}

	@Override
	public Object next() throws NoSuchElementException {
		try {
			return Array.get(this.array, this.index++);
		} catch (final ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
			final NoSuchElementException noSuchElementException = new NoSuchElementException("Cannot return next element, because there is none!");
			noSuchElementException.initCause(arrayIndexOutOfBoundsException);
			throw noSuchElementException;
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
