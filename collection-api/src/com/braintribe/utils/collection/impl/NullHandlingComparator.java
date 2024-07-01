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
package com.braintribe.utils.collection.impl;

import java.util.Comparator;

/**
 * A wrapper for a standard keyComparator, but this one is capable of comparing
 * <tt>null<tt>s, specifically in such way, that <tt>null</tt> is the smallest value.
 */
public final class NullHandlingComparator<T> implements Comparator<T> {

	private final Comparator<T> comparator;

	public NullHandlingComparator(Comparator<? super T> comparator) {
		super();

		Comparator<T> c = (Comparator<T>) comparator;

		// just in case, to avoid unnecessary useless decoration
		this.comparator = c instanceof NullHandlingComparator ? ((NullHandlingComparator<T>) c).comparator : c;
	}

	@Override
	public int compare(T a, T b) {
		if (a == null) {
			return b == null ? 0 : -1;

		} else {
			return b == null ? 1 : comparator.compare(a, b);
		}
	}

}
