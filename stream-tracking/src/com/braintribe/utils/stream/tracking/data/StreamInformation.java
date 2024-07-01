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
package com.braintribe.utils.stream.tracking.data;

public class StreamInformation {

	private String tenant;
	private String streamId;
	private boolean streamClosed = false;
	private long streamCreatedTimestamp = System.nanoTime();
	private long streamDuration = -1;
	private String context;
	private String clientIdentifier;
	public long bytesTransferred = 0L; // Note: keeping this public to grant direct (i.e. quick) access
	private boolean streamEndReached = false;

	public StreamInformation(String tenant, String streamId, String clientIdentifier, String ctx) {
		this.tenant = tenant;
		this.clientIdentifier = clientIdentifier;
		this.streamId = streamId;
		this.context = ctx;
	}

	public boolean closed() {
		if (!streamClosed) {
			streamClosed = true;
			streamDuration = System.nanoTime() - streamCreatedTimestamp;
			return true;
		}
		return false;
	}
	public long duration() {
		return streamDuration;
	}
	public boolean isClosed() {
		return streamClosed;
	}
	public String getContext() {
		return context;
	}
	public String getClientIdentifier() {
		return clientIdentifier;
	}

	public long getBytesTransferred() {
		return bytesTransferred;
	}
	public String getStreamId() {
		return streamId;
	}

	public long age() {
		if (streamDuration == -1) {
			return System.nanoTime() - streamCreatedTimestamp;
		} else {
			return streamDuration;
		}
	}

	public void eofReached() {
		streamEndReached = true;
	}

	public boolean readFully() {
		return streamEndReached;
	}

	public String getTenant() {
		return tenant;
	}

	@Override
	public String toString() {
		return clientIdentifier + " (Context: " + context + ", Closed: " + streamClosed + ", Age: " + streamDuration + ")";
	}

}
