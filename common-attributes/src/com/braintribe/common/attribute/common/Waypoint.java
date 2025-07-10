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
package com.braintribe.common.attribute.common;

import com.braintribe.common.attribute.TypeSafeAttribute;

/**
 * Denotes a relevant point along the code execution path.
 * <p>
 * Can be used for marking an endpoint or some other important point along the way.
 * <p>
 * It is intended for implementing security features.
 */
public interface Waypoint extends TypeSafeAttribute<String> {
	// empty
}
