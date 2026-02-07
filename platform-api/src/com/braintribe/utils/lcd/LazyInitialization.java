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

/**
 * Convenient wrapper for code that should only be executed once, with an optional cleanup.
 * <p>
 * This implementation is thread safe.
 *
 * @see #run()
 * @see Lazy
 */
public class LazyInitialization {

	private Lazy<Void> lazyInitialized;

	public LazyInitialization(Runnable runnable) {
		this(runnable, (Consumer<Void>) null);
	}

	public LazyInitialization(Runnable runnable, Runnable cleanup) {
		this(runnable, it -> cleanup.run());
	}

	private LazyInitialization(Runnable runnable, Consumer<Void> cleanup) {
		this.lazyInitialized = new Lazy<>(() -> {
			runnable.run();
			return null;
		}, cleanup);
	}

	/**
	 * Runs the initialization {@link Runnable} provided via constructor, but only on first invocation of this method. All subsequent invocations have
	 * no effect, until {@link #shutDown()} is called which would reset the state.
	 */
	public void run() {
		lazyInitialized.get();
	}

	/**
	 * Runs the cleanup {@link Runnable} if provided via constructor, but only on first invocation of this method. All subsequent invocations have no
	 * effect.
	 * <p>
	 * Resets the state, so calling {@link #run()} again would run the initialization again .
	 */
	public void shutDown() {
		lazyInitialized.close();
	}

}
