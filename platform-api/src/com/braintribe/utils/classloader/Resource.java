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
package com.braintribe.utils.classloader;

import java.net.URL;

import com.braintribe.utils.Not;

public class Resource {

	protected final String resourceName;
	protected final ClassLoader loader;

	Resource(String resourceName, ClassLoader loader) {
		this.resourceName = Not.Null(resourceName);
		this.loader = Not.Null(loader);
	}

	static Resource create(String resourceName, ClassLoader loader) {
		if (resourceName.endsWith(".class")) {
			return new ClassResource(resourceName, loader);
		} else {
			return new Resource(resourceName, loader);
		}
	}

	/** Returns the url identifying the resource. */
	public final URL url() {
		return Not.Null(loader.getResource(resourceName), "Failed to load resource: %s", resourceName);
	}

	/** Returns the fully qualified name of the resource. Such as "com/mycomp/foo/bar.txt". */
	public final String getResourceName() {
		return resourceName;
	}

	@Override
	public int hashCode() {
		return resourceName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Resource) {
			Resource that = (Resource) obj;
			return resourceName.equals(that.resourceName) && loader == that.loader;
		}
		return false;
	}

	@Override
	public String toString() {
		return resourceName;
	}
}
