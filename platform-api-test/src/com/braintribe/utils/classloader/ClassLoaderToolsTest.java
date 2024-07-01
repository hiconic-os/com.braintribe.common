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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

@Ignore
public class ClassLoaderToolsTest {

	@Test
	public void testGetAllClassesSizeCompare() throws Exception {

		ClassLoader classLoader = ClassLoaderToolsTest.class.getClassLoader();
		Set<String> classes = ClassLoaderTools.getAllClasses(classLoader);

		ClassPath cp = ClassPath.from(classLoader);
		Set<ClassInfo> classesFromGuava = cp.getAllClasses();

		assertThat(classes.size()).isEqualTo(classesFromGuava.size());
	}

	@Test
	public void testGetAllClassesDeepCompare() throws Exception {

		ClassLoader classLoader = ClassLoaderToolsTest.class.getClassLoader();
		Set<String> classes = ClassLoaderTools.getAllClasses(classLoader);
		TreeSet<String> sortedClasses = new TreeSet<>(classes);

		ClassPath cp = ClassPath.from(classLoader);
		Set<ClassInfo> classesFromGuava = cp.getAllClasses();
		TreeSet<String> sortedGuavaClasses = new TreeSet<>();
		for (ClassInfo ci : classesFromGuava) {
			sortedGuavaClasses.add(ci.getName());
		}

		Iterator<String> classIterator = sortedClasses.iterator();
		Iterator<String> guavaIterator = sortedGuavaClasses.iterator();

		while (classIterator.hasNext()) {
			String className = classIterator.next();
			String guavaClassName = guavaIterator.next();
			Assert.assertEquals(guavaClassName, className);
		}
		Assert.assertFalse(guavaIterator.hasNext());
	}
}
