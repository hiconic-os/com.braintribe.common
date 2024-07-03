// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
//
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
package com.braintribe.utils;

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.UUID;

import org.junit.After;
import org.junit.Test;

public class FileToolsCopyDirectoryTest {

	private File target;

	@Test
	public void testDirCopy() throws IOException {
		File source = FileTools.getFile("res/DirCopyTest/", true, false, false, true, true);
		target = FileTools.createNewTempDir("tempFile" + UUID.randomUUID());
		FileTools.copyDirectory(source, target);

		File file1 = new File(target, "folder1/file1");
		File file2 = new File(target, "folder2/file2");
		File file11 = new File(target, "folder1/folder11/file11");

		File emptyFolder = new File(target, "emptyFolder");

		assertFilesExistAndAreFiles(file1, file2, file11);
		assertFilesExistAndAreDirectories(emptyFolder);
	}

	@Test(expected = UncheckedIOException.class)
	public void testDirCopy_noFollowLinks() throws IOException {
		if (OsTools.getOperatingSystem() == OsTools.OperatingSystem.Linux) {
			File source = FileTools.getFile("res/DirCopyTestWithLinks/", true, false, false, true, true);
			target = FileTools.createNewTempDir("tempFile" + UUID.randomUUID());
			// fails with
			//   java.io.UncheckedIOException: Error while copying file /path/to/platform-api-test/res/DirCopyTestWithLinks/linkFolder1 to /tmp/tempFile03b8f1c6-4be0-4549-bd4f-f8c73eea3708/linkFolder1.
			//   ...
			//   Caused by: java.io.FileNotFoundException: res/DirCopyTestWithLinks/linkFolder1 (Is a directory)
			// (error says it's a directory, but it's actually a symlink to that folder) 
			FileTools.copyDirectory(source, target);
		} else {
			throw new UncheckedIOException(new IOException("test implemented only for Linux"));
		}
	}

	@Test
	public void testDirCopy_followLinks() throws IOException {
		if (OsTools.getOperatingSystem().equals(OsTools.OperatingSystem.Linux)) {
			File source = FileTools.getFile("res/DirCopyTestWithLinks/", true, false, false, true, true);
			target = FileTools.createNewTempDir("tempFile" + UUID.randomUUID());
			FileTools.copyDirectory(source, target, true);

			File file1 = new File(target, "folder1/file1");
			File file2 = new File(target, "folder2/file2");
			File file11 = new File(target, "folder1/folder11/file11");
			File linkFile1 = new File(target, "linkFile1");

			File linkFolder1 = new File(target, "linkFolder1");
			File linkFolder1_file1 = new File(target, "linkFolder1/file1");
			File linkFolder1_file11 = new File(target, "linkFolder1/folder11/file11");

			assertFilesExistAndAreFiles(file1, file2, file11, linkFile1, linkFolder1_file1, linkFolder1_file11);
			assertFilesExistAndAreDirectories(linkFolder1);
		}
	}

	@After
	public void cleanUp() throws IOException {
		FileTools.deleteDirectoryRecursively(target);
	}

	private void assertFilesExistAndAreFiles(File... files) {
		for (File file : files) {
			assertThat(file).exists();
			assertThat(file).isFile();
		}
	}

	private void assertFilesExistAndAreDirectories(File... files) {
		for (File file : files) {
			assertThat(file).exists();
			assertThat(file).isDirectory();
		}
	}
}
