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

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import com.braintribe.utils.IOTools;

public class DeleteOnCloseFileInputStreamTest {

	@Test
	public void testDeleteOnClose() throws Exception {

		File tmpFile = File.createTempFile("test", ".tmp");
		IOTools.spit(tmpFile, "Hello, world!", "UTF-8", false);

		DeleteOnCloseFileInputStream in = new DeleteOnCloseFileInputStream(tmpFile);
		FileInputStream fis = new FileInputStream(tmpFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		String actual = reader.readLine().trim();

		assertThat(tmpFile).exists();
		reader.close();
		in.close();
		assertThat(tmpFile).doesNotExist();

		assertThat(actual).isEqualTo("Hello, world!");

	}

	@Test
	public void testDeleteOnCloseWithFolder() throws Exception {

		File tmpFolder = File.createTempFile("folder", ".tmp");
		tmpFolder.delete();
		tmpFolder.mkdirs();

		File tmpFile = File.createTempFile("test", ".tmp", tmpFolder);

		IOTools.spit(tmpFile, "Hello, world!", "UTF-8", false);

		DeleteOnCloseFileInputStream in = new DeleteOnCloseFileInputStream(tmpFile, true);
		FileInputStream fis = new FileInputStream(tmpFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		String actual = reader.readLine().trim();

		assertThat(tmpFile).exists();
		assertThat(tmpFolder).exists();
		reader.close();
		in.close();
		assertThat(tmpFile).doesNotExist();
		assertThat(tmpFolder).doesNotExist();

		assertThat(actual).isEqualTo("Hello, world!");

	}

	@Test
	public void testDeleteOnCloseWithNotEmptyFolder() throws Exception {

		File tmpFolder = File.createTempFile("folder", ".tmp");
		tmpFolder.delete();
		tmpFolder.mkdirs();

		File tmpFile = File.createTempFile("test", ".tmp", tmpFolder);
		File tmpFile2 = File.createTempFile("test2", ".tmp", tmpFolder);

		IOTools.spit(tmpFile, "Hello, world!", "UTF-8", false);

		DeleteOnCloseFileInputStream in = new DeleteOnCloseFileInputStream(tmpFile, true);
		FileInputStream fis = new FileInputStream(tmpFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		String actual = reader.readLine().trim();

		assertThat(tmpFile).exists();
		assertThat(tmpFolder).exists();
		reader.close();
		in.close();
		assertThat(tmpFile).doesNotExist();
		assertThat(tmpFolder).exists();
		assertThat(tmpFile2).exists();

		assertThat(actual).isEqualTo("Hello, world!");

		tmpFile2.delete();
		tmpFolder.delete();
	}
}
