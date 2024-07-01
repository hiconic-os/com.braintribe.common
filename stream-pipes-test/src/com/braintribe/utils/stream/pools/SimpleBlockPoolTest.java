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
package com.braintribe.utils.stream.pools;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.braintribe.utils.stream.blocks.Block;

public class SimpleBlockPoolTest extends AbstractBlockPoolTest {

	@Override
	BlockPool blockPool() {
		return new GrowingBlockPool(blockSupplier, poolSize());
	}

	@Override
	int poolSize() {
		return 10;
	}

	/**
	 * Assert that all blocks are reused after returning them to the pool.
	 */
	@Test
	public void testReturningAll() {

		BlockPool pool = blockPool();

		Set<Block> recievedBlocks = new HashSet<>();

		for (int i = 0; i < poolSize(); i++) {
			Block block = pool.get();
			recievedBlocks.add(block);
			assertThat(block).isNotNull();
		}

		assertThat(pool.get()).isNull(); // pool is now empty

		recievedBlocks.forEach(pool::giveBack);

		Set<Block> recievedBlocks2 = new HashSet<>();

		for (int i = 0; i < poolSize(); i++) {
			Block block = pool.get();
			recievedBlocks2.add(block);
			assertThat(block).isNotNull();
		}

		assertThat(pool.get()).isNull(); // pool is again empty

		assertThat(recievedBlocks).hasSameElementsAs(recievedBlocks2);
	}
}
