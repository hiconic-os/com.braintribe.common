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
package com.braintribe.utils.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import org.junit.Test;

import com.braintribe.utils.IOTools;

public class CountingOutputStreamTest {

	@Test
	public void testCountingOuputStream() {

		CountingOutputStream cos = new CountingOutputStream(new ByteArrayOutputStream());
		assertThat(cos.getCount()).isEqualTo(0L);
	}

	@Test
	public void testCountingOuputStreamMultipleRandom() throws Exception {

		int runs = 100;
		Random rnd = new Random();

		for (int i = 0; i < runs; ++i) {

			int size = rnd.nextInt(100000);

			byte[] data = new byte[size];
			rnd.nextBytes(data);

			ByteArrayInputStream bis = null;
			ByteArrayOutputStream baos = null;

			CountingOutputStream cos = null;
			try {
				bis = new ByteArrayInputStream(data);
				baos = new ByteArrayOutputStream();
				cos = new CountingOutputStream(baos);

				IOTools.pump(bis, cos);

			} finally {
				IOTools.closeCloseable(bis, null);
				IOTools.closeCloseable(cos, null);
			}

			assertThat(cos.getCount()).isEqualTo(size);
			assertThat(baos.toByteArray()).isEqualTo(data);

		}

	}
}
