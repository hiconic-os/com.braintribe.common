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
package com.braintribe.testing.junit.assertions.assertj.core.api;

import org.assertj.core.api.AbstractCharSequenceAssert;

import com.braintribe.logging.Logger;

/**
 * Provides custom {@link CharSequence} assertions.
 *
 * @author michael.lafite
 */
public class ExtendedCharSequenceAssert extends AbstractCharSequenceAssert<ExtendedCharSequenceAssert, CharSequence>
		implements SharedCharSequenceAssert<ExtendedCharSequenceAssert, CharSequence> {

	@SuppressWarnings("unused") // may be used by SharedAssert methods via reflection
	private static final Logger logger = Logger.getLogger(ExtendedCharSequenceAssert.class);

	public ExtendedCharSequenceAssert(CharSequence actual) {
		super(actual, ExtendedCharSequenceAssert.class);
	}
}
