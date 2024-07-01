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
/**
 * 
 */
package com.braintribe.processing.async.impl;

import com.braintribe.logging.Logger;
import com.braintribe.logging.Logger.LogLevel;
import com.braintribe.processing.async.api.AsyncCallback;

public class LoggingAsyncCallback<T> implements AsyncCallback<T> {

	private static final Logger logger = Logger.getLogger(LoggingAsyncCallback.class);

	private String context;
	private LogLevel successLogLevel = LogLevel.DEBUG;
	private LogLevel failureLogLevel = LogLevel.INFO;

	public static <T> LoggingAsyncCallback<T> create(String context) {
		return new LoggingAsyncCallback<>(context);
	}
	public static <T> LoggingAsyncCallback<T> create(String context, LogLevel successLogLevel, LogLevel failureLogLevel) {
		LoggingAsyncCallback<T> bean = new LoggingAsyncCallback<>(context);
		bean.successLogLevel = successLogLevel;
		bean.failureLogLevel = failureLogLevel;
		return bean;
	}

	private LoggingAsyncCallback(String context) {
		this.context = context;
	}
	@Override
	public void onSuccess(Object future) {
		if (logger.isLevelEnabled(successLogLevel)) {
			logger.log(successLogLevel, "Asynchronous request with context '" + context + "' was successful. Result: " + future);
		}
	}

	@Override
	public void onFailure(Throwable t) {
		if (logger.isLevelEnabled(failureLogLevel)) {
			logger.log(failureLogLevel, "Asynchronous request with context '" + context + "' was not successful.", t);
		}
	}
}
