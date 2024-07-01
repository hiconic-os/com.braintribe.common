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
package com.braintribe.utils.file.compare;

import static com.braintribe.utils.lcd.CollectionTools2.newList;
import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Result of a comparison of two directories. We call the two directories <tt>leftDir</tt> and <tt>rightDir</tt>, and based on that the result
 * consists of those file/dirs present only in the left dir, those only in the right dir, and those which are present in both, which is are stored in
 * {@link #leftToRight}.
 *
 * @author peter.gazdik
 */
public class FolderComparison {

	public File leftDir;
	public File rightDir;

	/** Files present in <tt>leftDir</tt> with no equivalent in <tt>rightDir</tt> */
	public final List<File> onlyLeft = newList();

	/** Files present in <tt>rightDir</tt> with no equivalent in <tt>leftDir</tt> */
	public final List<File> onlyRight = newList();

	/** Maps files from <tt>leftDir</tt> to their equivalent counterpart in <tt>rightDir</tt>. */
	public final Map<File, File> leftToRight = newMap();

}
