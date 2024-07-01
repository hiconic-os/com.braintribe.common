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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This output stream writes any content written to it to an arbitrary number of other {@link OutputStream} delegates
 *
 * @author Dirk Scheffler
 *
 */
public class MultiplierOutputStream extends OutputStream {
	private List<OutputStream> delegates;

	public MultiplierOutputStream(List<OutputStream> delegates) {
		super();
		this.delegates = delegates;
	}

	public MultiplierOutputStream(Iterable<File> files) throws IOException {
		delegates = new ArrayList<>();
		for (File file : files) {
			delegates.add(new FileOutputStream(file));
		}
	}

	@Override
	public void write(int b) throws IOException {
		for (OutputStream delegate : delegates) {
			delegate.write(b);
		}
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		for (OutputStream delegate : delegates) {
			delegate.write(b, off, len);
		}
	}

	@Override
	public void write(byte[] b) throws IOException {
		for (OutputStream delegate : delegates) {
			delegate.write(b);
		}
	}

	@Override
	public void flush() throws IOException {
		for (OutputStream delegate : delegates) {
			delegate.flush();
		}
	}

	@Override
	public void close() throws IOException {
		for (OutputStream delegate : delegates) {
			delegate.close();
		}
	}
}
