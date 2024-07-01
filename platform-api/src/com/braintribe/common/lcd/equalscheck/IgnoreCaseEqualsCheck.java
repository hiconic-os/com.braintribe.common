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
package com.braintribe.common.lcd.equalscheck;

/**
 * Checks if two strings are equal ignoring the case.
 *
 * @author michael.lafite
 */
public class IgnoreCaseEqualsCheck implements EqualsCheck<String> {

	public IgnoreCaseEqualsCheck() {
		// nothing to do
	}

	/**
	 * Returns <code>true</code>, if both objects are <code>null</code>, or if <code>object1</code> {@link String#equalsIgnoreCase(String)
	 * equalsIgnoreCase} <code>object2</code>, otherwise <code>false</code>.
	 */
	@Override
	public boolean equals(final String object1, final Object object2) {
		if (object1 == null) {
			return (object2 == null);
		}
		if (!(object2 instanceof String)) {
			return false;
		}

		return object1.equalsIgnoreCase((String) object2);
	}
}
