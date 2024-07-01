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
package com.braintribe.utils.lcd.graph;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Experimental, use at your own risk.
 *
 * @author peter.gazdik
 */
public interface StreamBuilder<N> {

	StreamBuilder2<N> withNeighbors(Function<N, ? extends Collection<? extends N>> neighborFunction);

	StreamBuilder2<N> withNeighborStream(Function<N, ? extends Stream<? extends N>> neighborFunction);

	/**
	 * This is intended as the second step of the builder, after the neighbor function was provided via {@link StreamBuilder}
	 */
	public static interface StreamBuilder2<N> {

		StreamBuilder2<N> distinct();

		/** Stream elements using given traversal order. Default is post-order. */
		StreamBuilder2<N> withOrder(TraversalOrder order);

		Stream<N> please();

	}

	public static enum TraversalOrder {
		// might implement breadth-first in the future
		preOrder,
		postOrder;
	}

}
