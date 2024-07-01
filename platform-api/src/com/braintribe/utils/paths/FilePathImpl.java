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
package com.braintribe.utils.paths;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

import com.braintribe.utils.FileTools;

/**
 * @author peter.gazdik
 */
/* package */ class FilePathImpl implements FilePath {

	private final File file;

	public FilePathImpl(String pathname) {
		this(new File(pathname));
	}

	public FilePathImpl(File file) {
		this.file = file;
	}

	@Override
	public FilePath parent() {
		return new FilePathImpl(file.getParentFile());
	}

	@Override
	public FilePath nthParent(int n) {
		File result = file;
		for (int i = 0; i < n; i++) {
			result = file.getParentFile();
			if (result == null) {
				throw new IllegalArgumentException(
						"Cannot get " + n + "-th parent of file '" + file.getPath() + "', as there are only " + i + " parents on this path.");
			}
		}

		return new FilePathImpl(result);
	}

	@Override
	public FilePath child(String... childSequence) {
		File result = file;
		for (String child : requireNonNull(childSequence)) {
			result = new File(result, child);
		}

		return new FilePathImpl(result);
	}

	@Override
	public FilePath sybling(String siblingName) {
		return parent().child(siblingName);
	}

	@Override
	public File toFile() {
		return file;
	}

	@Override
	public URL toURL() {
		return FileTools.toURL(file);
	}

	@Override
	public Path toPath() {
		return file.toPath();
	}

}
