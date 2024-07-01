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
package com.braintribe.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class UrlTools {

	public static String encodeQuery(final Map<String, String> parameterMap) throws UnsupportedEncodingException {
		final StringBuilder sb = new StringBuilder();
		for (final Map.Entry<String, String> entry : parameterMap.entrySet()) {
			final String encodedKey = URLEncoder.encode(entry.getKey(), "UTF-8");
			final String encodedVal = URLEncoder.encode(entry.getValue(), "UTF-8");
			if (sb.length() == 0) {
				sb.append("?");
			} else {
				sb.append("&");
			}
			sb.append(encodedKey);
			sb.append("=");
			sb.append(encodedVal);
		}
		return sb.toString();
	}
}
