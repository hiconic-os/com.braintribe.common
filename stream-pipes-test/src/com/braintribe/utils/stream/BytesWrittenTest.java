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
package com.braintribe.utils.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import com.braintribe.testing.test.AbstractTest;
import com.braintribe.utils.IOTools;
import com.braintribe.utils.stream.api.PipeStatus;
import com.braintribe.utils.stream.api.StreamPipe;
import com.braintribe.utils.stream.api.StreamPipeFactory;
import com.braintribe.utils.stream.api.StreamPipes;

/**
 * Tests {@link StreamPipe#bytesWritten()} for both pipe implementations.
 */
public class BytesWrittenTest extends AbstractTest {

	/** Big enough to span all block sizes of the {@link StreamPipes#simpleFactory()}, i.e. 4K, 64K and 1M. */
	private static final int BIG = 3_000_000;

	@Test
	public void blockBackedPipe() throws Exception {
		testBytesWritten(StreamPipes.simpleFactory());
	}

	@Test
	public void fileBackedPipe() throws Exception {
		testBytesWritten(StreamPipes.fileBackedFactory());
	}

	@Test
	public void blockBackedPipe_NothingWritten() throws Exception {
		testNothingWritten(StreamPipes.simpleFactory());
	}

	@Test
	public void fileBackedPipe_NothingWritten() throws Exception {
		testNothingWritten(StreamPipes.fileBackedFactory());
	}

	private void testBytesWritten(StreamPipeFactory factory) throws Exception {
		StreamPipe pipe = factory.newPipe("bytesWritten");
		try {
			assertThat(pipe.bytesWritten()).isEqualTo(0L);

			OutputStream out = pipe.openOutputStream();

			// While the pipe is still being fed the value reflects what was written so far
			out.write(new byte[1000]);
			out.flush();
			assertThat(pipe.bytesWritten()).isEqualTo(1000L);
			assertThat(pipe.getStatus()).isEqualTo(PipeStatus.feeding);

			out.write(new byte[BIG]);
			out.write(42); // single byte write, to also cover write(int)
			out.close();

			long expected = 1000L + BIG + 1;

			assertThat(pipe.getStatus()).isEqualTo(PipeStatus.completed);
			assertThat(pipe.bytesWritten()).isEqualTo(expected);

			// The whole point of the value is to tell how big the content is without reading it, so it better match
			assertThat(readableBytes(pipe)).isEqualTo(expected);

		} finally {
			pipe.close();
		}
	}

	private void testNothingWritten(StreamPipeFactory factory) throws Exception {
		StreamPipe pipe = factory.newPipe("bytesWritten-empty");
		try {
			pipe.openOutputStream().close();

			assertThat(pipe.getStatus()).isEqualTo(PipeStatus.completed);
			assertThat(pipe.bytesWritten()).isEqualTo(0L);
			assertThat(readableBytes(pipe)).isEqualTo(0L);

		} finally {
			pipe.close();
		}
	}

	private long readableBytes(StreamPipe pipe) throws Exception {
		try (InputStream in = pipe.openInputStream()) {
			long result = 0;
			byte[] buffer = new byte[IOTools.SIZE_64K];

			int read;
			while ((read = in.read(buffer)) != -1)
				result += read;

			return result;
		}
	}

}
