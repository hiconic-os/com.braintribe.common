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
package com.braintribe.utils.io;

import java.io.IOException;
import java.io.Writer;

/**
 * {@link Writer} implementation which overrides the key methods to avoid synchronization on every write and instead stores characters in an internal
 * buffer (up to the buffer's limit). When the buffer is full, its content is written to the underlying Writer.
 * <p>
 * NOTE: When used as a wrapper to a Makes sure to {@link #flush() flush} this buffer once the writing is finished.
 * <p>
 * Example when used as a wrapper for a passed in Writer:
 * 
 * <pre>
 * Writer unsyncedWriter = UnsynchronizedBufferedWriter.ensureUnsynchronized(writer);
 * doWriting(unsyncedWriter)
 * unsyncedWriter.flush();
 * </pre>
 */
public class UnsynchronizedBufferedWriter extends Writer {

	// Some tests with 64K buffer showed its slower. Tested only on one machine though.
	private static final int DEFAULT_BUFFER_SIZE = 32768;

	private final Writer delegate;
	private final char[] buffer;

	private int nextChar;

	public UnsynchronizedBufferedWriter ensureUnsynchronized(Writer writer) {
		if (writer instanceof UnsynchronizedBufferedWriter)
			return (UnsynchronizedBufferedWriter) writer;
		else
			return new UnsynchronizedBufferedWriter(writer);
	}

	public UnsynchronizedBufferedWriter(Writer delegate) {
		this(delegate, DEFAULT_BUFFER_SIZE);
	}

	public UnsynchronizedBufferedWriter(Writer delegate, int bufferSize) {
		this.delegate = delegate;

		this.buffer = new char[bufferSize];
		this.nextChar = 0;
	}

	@Override
	public void write(String str) throws IOException {
		write(str, 0, str.length());
	}

	@Override
	public void write(String str, int off, int len) throws IOException {
		if (len == 0)
			return;

		if (len >= buffer.length) {
			flushBuffer();
			delegate.write(str, off, len);
			return;
		}

		if (len > buffer.length - nextChar)
			flushBuffer();

		str.getChars(off, (off + len), buffer, nextChar);
		nextChar += len;
	}

	@Override
	public void write(int c) throws IOException {
		if (nextChar >= buffer.length)
			flushBuffer();

		buffer[nextChar++] = (char) c;
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		if (off < 0 || off > cbuf.length || len < 0 || (off + len) > cbuf.length || (off + len) < 0)
			throw new IndexOutOfBoundsException("Offset " + off + " and length " + len + " are out of bounds for array of length " + cbuf.length);

		if (len == 0)
			return;

		if (len >= buffer.length) {
			flushBuffer();
			delegate.write(cbuf, off, len);
			return;
		}

		if (len > buffer.length - nextChar)
			flushBuffer();

		System.arraycopy(cbuf, off, buffer, nextChar, len);
		nextChar += len;
	}

	private void flushBuffer() throws IOException {
		if (nextChar > 0) {
			delegate.write(buffer, 0, nextChar);
			nextChar = 0;
		}
	}

	@Override
	public void flush() throws IOException {
		flushBuffer();
		delegate.flush();
	}

	@Override
	public void close() throws IOException {
		flush();
		delegate.close();
	}

}