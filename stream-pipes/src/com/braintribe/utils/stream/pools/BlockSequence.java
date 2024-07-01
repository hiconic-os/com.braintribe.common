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

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import com.braintribe.collections.GrowingIterable;
import com.braintribe.utils.stream.SupplierBackedSequenceInputStream;
import com.braintribe.utils.stream.blocks.Block;

/**
 * Growing sequence of blocks.
 * <p>
 * Note, that this class itself isn't thread safe. The supplied {@link #inputStream()}s however can be used
 * concurrently, even when the blocks are fed from a (single) parallel thread ({@link #addNewBlock()}).
 * 
 * @author Neidhart.Orlich
 *
 */
class BlockSequence {
	private final Supplier<Block> blockPool;
	private GrowingIterable<Block> memberBlocks = new GrowingIterable<>();
	private final Set<SupplierBackedSequenceInputStream> openStreams = ConcurrentHashMap.newKeySet();

	public BlockSequence(Supplier<Block> blockPool) {
		this.blockPool = blockPool;
	}

	public Block addNewBlock() {
		Block newBlock = blockPool.get();
		memberBlocks.add(newBlock);

		return newBlock;
	}

	/**
	 * New input stream for the data stored in contained blocks that starts at the beginning. Can be called multiple
	 * times. It is possible that {@link InputStream#read()} returns <code>-1</code> but later on again has more data
	 * because a new block was added or further data was written to the last block.
	 */
	public InputStream inputStream() {
		SupplierBackedSequenceInputStream inputStream = new SupplierBackedSequenceInputStream(memberBlocks) {
			@Override
			public void close() throws IOException {
				super.close();
				openStreams.remove(this);
			}
		};
		openStreams.add(inputStream);
		return inputStream;
	}

	public Iterable<Block> asIterable() {
		return memberBlocks;
	}

	// Thread synchronization must happen outside so that no new InputStreams are opened while this method runs.
	public void close() {
		if (!openStreams.isEmpty()) {
			throw new IllegalStateException("Can't close StreamPipe because there is still an InputStream open.");
		}

		memberBlocks.forEach(Block::free);
		memberBlocks = new GrowingIterable<Block>();
	}
}
