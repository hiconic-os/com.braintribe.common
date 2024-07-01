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
package com.braintribe.utils.date;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import org.junit.Test;

import com.braintribe.utils.StringTools;

public class NanoClockTest {

	@Test
	public void testNanoClock() throws Exception {

		Clock clock = NanoClock.INSTANCE;

		Instant i1 = clock.instant();
		Thread.sleep(200L); // Stupid DevQA seems to have a terrible precision. In one test, the duration was 928121 ns
							// (instead of 2 ms).
		Instant i2 = clock.instant();

		Duration duration = Duration.between(i1, i2);

		assertThat(duration.getNano()).isGreaterThan(1_000_000);
		assertThat(duration.getNano() % 1_000_000).isGreaterThan(0); // this check is crucial as it checks whether
																		// nano-precision is given

		System.out.println(StringTools.prettyPrintDuration(duration, true, null));

	}
}
