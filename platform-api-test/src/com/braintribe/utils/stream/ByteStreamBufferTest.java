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
import java.io.File;
import java.util.Random;

import org.junit.Test;

import com.braintribe.utils.lcd.IOTools;

public class ByteStreamBufferTest {

	@Test
	public void testWithFile() throws Exception {

		byte[] bytes = new byte[1024];
		new Random().nextBytes(bytes);

		ByteStreamBuffer buffer = new ByteStreamBuffer(new ByteArrayInputStream(bytes), 32, true);
		File file = buffer.buffer.file;
		assertThat(file).isNotNull();
		assertThat(file).exists();
		assertThat(file.length()).isEqualTo(1024L);

		ByteArrayOutputStream actual = new ByteArrayOutputStream();
		IOTools.pump(buffer, actual);
		assertThat(file).doesNotExist();

		assertThat(actual.toByteArray()).isEqualTo(bytes);
	}

	@Test
	public void testWithoutFile() throws Exception {

		byte[] bytes = new byte[1024];
		new Random().nextBytes(bytes);

		ByteStreamBuffer buffer = new ByteStreamBuffer(new ByteArrayInputStream(bytes), IOTools.SIZE_1M, true);
		File file = buffer.buffer.file;
		assertThat(file).isNull();

		ByteArrayOutputStream actual = new ByteArrayOutputStream();
		IOTools.pump(buffer, actual);

		assertThat(actual.toByteArray()).isEqualTo(bytes);
	}
}
