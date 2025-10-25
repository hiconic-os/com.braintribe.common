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
package com.braintribe.utils.lcd;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @deprecated use {@link Lazy}
 */
@Deprecated
public class LazyInitialized<T> extends Lazy<T> {

	public LazyInitialized(Supplier<T> constructor) {
		super(constructor, null);
	}

	public LazyInitialized(Supplier<T> constructor, Consumer<T> destructor) {
		super(constructor, destructor);
	}

	@Override
	public T get() {
		return super.get();
	}

	@Override
	public boolean isInitialized() {
		return super.isInitialized();
	}

	@Override
	public void close() {
		super.close();
	}

}
