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
package pawotag._1.requirements.controller;

import pawotag._1.requirements.config.Configuration;

/**
 * Minimal StripesFilter for CryptoUtil.
 * getConfiguration() returns null, which makes CryptoUtil skip debug mode check.
 */
public class StripesFilter {

    private static Configuration configuration = null;

    /**
     * Returns the Configuration instance, or null if not initialized.
     */
    public static Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Sets the Configuration instance (for testing purposes).
     */
    public static void setConfiguration(Configuration config) {
        configuration = config;
    }
}
