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
package com.braintribe.console.output;

public class SingleElementConsoleOutputContainer implements ConsoleOutputContainer {
	private ConsoleOutput element;
	private int style;
	private boolean resetPosition;
	
	public SingleElementConsoleOutputContainer(int style, ConsoleOutput element) {
		super();
		this.style = style;
		this.element = element;
	}
	
	public SingleElementConsoleOutputContainer(int style, ConsoleOutput element, boolean resetPosition) {
		super();
		this.element = element;
		this.style = style;
		this.resetPosition = resetPosition;
	}

	@Override
	public int getStyle() {
		return style;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public ConsoleOutput get(int i) {
		return element;
	}
	
	@Override
	public boolean resetPosition() {
		return resetPosition;
	}
}
