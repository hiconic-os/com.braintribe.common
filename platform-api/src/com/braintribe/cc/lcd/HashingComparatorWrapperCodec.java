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
package com.braintribe.cc.lcd;

/**
 * Implementation of {@link HashSupportWrapperCodec} that uses a provided {@link HashingComparator} for equals and hashCode computations.
 * <p>
 * Instances may be passed as arguments to {@link CodingMap#createHashMapBased(com.braintribe.codec.Codec)}.
 *
 * @see HashingComparator
 * @see CodingMap
 */
public class HashingComparatorWrapperCodec<T> extends HashSupportWrapperCodec<T> {

	private final HashingComparator<? super T> comparator;

	public HashingComparatorWrapperCodec(final HashingComparator<? super T> comparator, final boolean entitiesAreImmutable) {
		super(entitiesAreImmutable);
		this.comparator = comparator;
	}

	@Override
	protected int entityHashCode(final T e) {
		return this.comparator.computeHash(e);
	}

	@Override
	protected boolean entityEquals(final T e1, final T e2) {
		return this.comparator.compare(e1, e2);
	}

}
