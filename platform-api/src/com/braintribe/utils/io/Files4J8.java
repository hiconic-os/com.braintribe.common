package com.braintribe.utils.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset))) {

			int bufferSize = IOTools.SIZE_16K;
			StringBuilder sb = new StringBuilder(bufferSize);
			char[] buffer = new char[bufferSize];

			while (true) {
				int charactersRead = reader.read(buffer);

				if (charactersRead < 0)
					return sb.toString();

				sb.append(buffer, 0, charactersRead);
			}

		} catch (IOException e) {
			throw new UncheckedIOException("Error while reading from file " + file + "!", e);
		}
	}

}
