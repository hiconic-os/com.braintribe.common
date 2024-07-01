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
package com.braintribe.utils.lcd.string;

import static com.braintribe.utils.lcd.string.StringDistance.damerauLevenshteinDistance;
import static com.braintribe.utils.lcd.string.StringDistance.levenshteinDistance;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * @author peter.gazdik
 */
public class StringDistanceTest {

	@Test
	public void damerauLevenshtein() {
		assertDamerauLevenshtein("", "", 0);
		assertDamerauLevenshtein("abc", "abc", 0);
		assertDamerauLevenshtein("a", "A", 1);
		assertDamerauLevenshtein("abc", "ab", 1);
		assertDamerauLevenshtein("ab", "ba", 1);
		assertDamerauLevenshtein("-ab-", "-ba-", 1);
		assertDamerauLevenshtein("-ab-cd-", "-ba-dc-", 2);
		assertDamerauLevenshtein("-ab-", "-bac-", 2);
		assertDamerauLevenshtein("ca", "abc", 3);
		assertDamerauLevenshtein("abc", "ca", 3);
	}

	private void assertDamerauLevenshtein(String s, String t, int expectedDiff) {
		int actualDiff = damerauLevenshteinDistance(s, t);
		assertThat(actualDiff).as("Wrong distance of [" + s + "] and [" + t + "]").isEqualTo(expectedDiff);
	}

	@Test
	public void levenshtein() {
		assertLevenshtein("", "", 0);
		assertLevenshtein("a", "", 1);
		assertLevenshtein("pit", "pot", 1);
		assertLevenshtein("brainon", "braintribe", 5);
		assertLevenshtein("braintribe", "brainon", 5);
	}

	private void assertLevenshtein(String s, String t, int expectedDiff) {
		int actualDiff = levenshteinDistance(s, t);
		assertThat(actualDiff).as("Wrong distance of [" + s + "] and [" + t + "]").isEqualTo(expectedDiff);
	}

}
