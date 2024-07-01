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
package com.braintribe.testing.category;

/**
 * Signals that the respective test is expected to take a very long time to execute (e.g. more than 5 minutes). This information can, for example, be
 * used by continuous integration pipelines to exclude these tests not just from pull requests, but also from regular periodic builds (which run every
 * few hours). Instead the test will only be run as part of special, e.g. nightly or weekly tests.
 *
 * Whether or not to mark a test as <code>VerySlow</code> not only depends on the expected execution time though, but also on the importance of the
 * test and how many other very (slow) tests there are. It may absolutely make sense to include a very slow test in periodic builds, if it is
 * important and if there aren't too many others. For further information see {@link Slow}.
 *
 * By marking a test as <code>VerySlow</code> one basically signals that the test is very slow AND that it thus should only be run when testing time
 * doesn't really matter.
 *
 * @author michael.lafite
 *
 * @see Slow
 */
public interface VerySlow extends Slow {
	// no methods required
}
