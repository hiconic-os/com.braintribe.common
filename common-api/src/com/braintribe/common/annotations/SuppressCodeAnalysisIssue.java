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
package com.braintribe.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * an annotation to tell the code analyzing tool-set (Zed for now) to suppress the issues expressed in the String payload, 
 * analogous to the compiler's 'SuppressWarnings' annotation.
 *  
 *  @SuppressCodeAnalysisIssues( value="PropertyNameLiteralMismatch" fingerprint="property:<property>")
 *  @SuppressCodeAnalysisIssues( value="UnexpectedField")
 *  
 * @author pit
 *
 */
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Repeatable( SuppressCodeAnalysisIssues.class)
public @interface SuppressCodeAnalysisIssue {
	/**
	 * @return - the standard string value, ie. the Issue's string representation 
	 */
	String value();
	/**
	 * @return - additional expression to add to the fingerprint generated by the location of the annotation 
	 */
	String fingerprint() default ""; 
}
