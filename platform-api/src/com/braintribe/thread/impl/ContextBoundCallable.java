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
package com.braintribe.thread.impl;

import java.util.List;
import java.util.concurrent.Callable;

import com.braintribe.thread.api.ThreadContextScope;

public class ContextBoundCallable<T> implements Callable<T> {

	private Callable<T> delegate;
	private List<ThreadContextScope> scopes;

	public ContextBoundCallable(Callable<T> delegate, List<ThreadContextScope> scopes) {
		this.delegate = delegate;
		this.scopes = scopes;
	}

	@Override
	public T call() throws Exception {

		int i = 0;
		try {
			for (; i < scopes.size(); ++i) {
				scopes.get(i).push();
			}
		} catch (Exception e) {
			throw new ThreadContextScopingRuntimeException("Could not set the context in step: " + i + ", which is " + scopes.get(i), e);
		}
		try {
			return this.delegate.call();
		} finally {
			int j = i - 1;
			try {
				for (; j >= 0; --j) {
					scopes.get(j).pop();
				}
			} catch (Exception e) {
				throw new ThreadContextScopingRuntimeException("Could not remove the context in step " + j + ", which is " + scopes.get(j), e);
			}
		}
	}

	@Override
	public String toString() {
		return this.delegate.toString();
	}
	@Override
	public int hashCode() {
		return this.delegate.hashCode();
	}
	@Override
	public boolean equals(Object o) {
		return this.delegate.equals(o);
	}
}
