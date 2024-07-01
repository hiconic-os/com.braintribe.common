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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CollectionToolsTest {

	@Test
	public void testSplit() throws Exception {

		List<String> list = new ArrayList<>();
		List<List<String>> split = null;

		list.clear();
		list.add("hello");
		list.add("world");

		assertThat(CollectionTools.split(list, 1).size()).isEqualTo(2);
		assertThat(CollectionTools.split(list, 2).size()).isEqualTo(1);
		assertThat(CollectionTools.split(list, 3).size()).isEqualTo(1);

		list.clear();
		list.add("0");
		list.add("1");
		list.add("2");
		list.add("0");
		list.add("1");
		list.add("2");
		list.add("0");
		list.add("1");
		list.add("2");

		split = CollectionTools.split(list, 3);
		for (List<String> subList : split) {
			for (int i = 0; i < 3; ++i) {
				assertThat(subList.get(i)).isEqualTo("" + i);
			}
		}

		list.clear();
		list.add("0");
		list.add("1");
		list.add("2");
		list.add("0");
		list.add("1");
		list.add("2");
		list.add("0");
		list.add("1");

		split = CollectionTools.split(list, 3);
		for (List<String> subList : split) {
			for (int i = 0; i < subList.size(); ++i) {
				assertThat(subList.get(i)).isEqualTo("" + i);
			}
		}

		list.clear();

		split = CollectionTools.split(list, 3);
		assertThat(split.size()).isEqualTo(0);

		try {
			split = CollectionTools.split(null, 3);
			fail("This should have thrown a NullPointerException");
		} catch (NullPointerException npe) {
			// expected
		}

		list.clear();
		list.add("0");

		try {
			split = CollectionTools.split(list, -3);
			fail("This should have thrown an IllegalArgumentException");
		} catch (IllegalArgumentException iae) {
			// expected
		}

	}

}
