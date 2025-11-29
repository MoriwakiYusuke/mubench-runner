/* Copyright 2005-2006 Tim Fennell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pawotag._1.requirements.config;

/**
 * Minimal Configuration interface for CryptoUtil.
 * Only includes methods actually used by CryptoUtil.
 */
public interface Configuration {

    /** Returns true if debug mode is enabled. */
    boolean isDebugMode();

    /** Returns the BootstrapPropertyResolver for this configuration. */
    BootstrapPropertyResolver getBootstrapPropertyResolver();
}
