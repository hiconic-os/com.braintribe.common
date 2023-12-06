package com.braintribe.utils.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import com.braintribe.utils.IOTools;

/**
 * Because we cannot use some methods of {@link Files} that came after Java 8.
 * 
 * @author peter.gazdik
 */
public class Files4J8 {

	public static String readString(File file, Charset charset) throws UncheckedIOException {
		BufferedReader reader = null;
		IOException exceptionWhileReading = null;

		try {
			final InputStream inputStream = new FileInputStream(file);
			final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
			reader = new BufferedReader(inputStreamReader);
			final int bufferSize = IOTools.SIZE_8K;
			final StringBuilder stringBuilder = new StringBuilder(bufferSize);
			final char[] buffer = new char[bufferSize];

			while (true) {
				final int charactersRead = reader.read(buffer);
				if (charactersRead < 0) {
					break;
				}
				stringBuilder.append(String.valueOf(buffer, 0, charactersRead));
			}
			return stringBuilder.toString();

		} catch (final IOException e) {
			exceptionWhileReading = e;
			throw new UncheckedIOException("Error while reading from file " + file + "!", e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (final IOException exceptionWileClosingStream) {
				if (exceptionWhileReading == null) {
					throw new UncheckedIOException("Error while closing stream after successfully reading from " + file + "!",
							exceptionWileClosingStream);
				} else {
					exceptionWhileReading.addSuppressed(exceptionWileClosingStream);
				}
			}
		}
	}

}
