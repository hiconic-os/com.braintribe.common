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
package com.braintribe.common.artifact;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class StandardArtifactReflection implements ArtifactReflection {

	private String groupId;
	private String artifactId;
	private String version;
	private Set<String> archetypes;
	private String name;
	private String versionedName;

	public StandardArtifactReflection(String groupId, String artifactId, String version, String archetype) {
		this(groupId, artifactId, version, Collections.singleton(archetype));
	}
	public StandardArtifactReflection(String groupId, String artifactId, String version, Set<String> archetypes) {
		super();
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
		this.archetypes = archetypes;
		this.name = groupId + ":" + artifactId;
		this.versionedName = name + "#" + version;
	}

	@Override
	public int hashCode() {
		return Objects.hash(artifactId, groupId, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StandardArtifactReflection other = (StandardArtifactReflection) obj;
		return Objects.equals(artifactId, other.artifactId) && Objects.equals(groupId, other.groupId) && Objects.equals(version, other.version);
	}

	@Override
	public String artifactId() {
		return artifactId;
	}

	@Override
	public String groupId() {
		return groupId;
	}

	@Override
	public String version() {
		return version;
	}

	@Override
	public Set<String> archetypes() {
		return archetypes;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String versionedName() {
		return versionedName;
	}

	@Override
	public String toString() {
		return versionedName();
	}

}
