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
package com.braintribe.ansi;

import com.braintribe.logging.Logger;
import com.braintribe.utils.OsTools;

/**
 * @author peter.gazdik
 */
public class AnsiTools {

	public static final boolean IS_WINDOWS = OsTools.isWindowsOperatingSystem();

	private static final Logger log = Logger.getLogger(AnsiTools.class);
	
	// See org.fusesource.jansi.AnsiConsole
	public static final boolean IS_CYGWIN = IS_WINDOWS //
			&& startsWith(System.getenv("PWD"), "/") //
			&& !"cygwin".equals(System.getenv("TERM"));

	/* See org.fusesource.jansi.AnsiConsole - this was modified, AnsiConsole checks for equality with xterm, git bash says xterm-256color */
	public static final boolean IS_MINGW_XTERM = IS_WINDOWS //
			&& startsWith(System.getenv("MSYSTEM"), "MINGW") //
			&& startsWith(System.getenv("TERM"), "xterm");

	private static boolean startsWith(String nullableString, String prefix) {
		return nullableString != null && nullableString.startsWith(prefix);
	}

	public static boolean isAnsiStdout() {
		return System.console().isTerminal();
	}

	public static boolean isAnsiStderr() {
		// due to the lack of a given API (maybe upcoming will be: Console.isTerminal, Files.isTerminal) we use the normal console to detect which is maybe wrong 
		return System.console().isTerminal();
	}
}
