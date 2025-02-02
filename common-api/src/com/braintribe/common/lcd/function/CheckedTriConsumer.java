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
package com.braintribe.common.lcd.function;

import java.util.Objects;
import java.util.function.Consumer;

import jsinterop.annotations.JsType;
import jsinterop.context.JsInteropNamespaces;

/**
 * @author peter.gazdik
 */
@FunctionalInterface
@JsType(namespace = JsInteropNamespaces.util)
public interface CheckedTriConsumer<T, U, V, E extends Throwable> {

	void accept(T t, U u, V v) throws E;

	/**
	 * Similar to {@link Consumer#andThen(Consumer)}
	 */
	default CheckedTriConsumer<T, U, V, E> andThen(CheckedTriConsumer<? super T, ? super U, ? super V, ? extends E> after) {
		Objects.requireNonNull(after);
		return (t, u, v) -> {
			accept(t, u, v);
			after.accept(t, u, v);
		};
	}

}
