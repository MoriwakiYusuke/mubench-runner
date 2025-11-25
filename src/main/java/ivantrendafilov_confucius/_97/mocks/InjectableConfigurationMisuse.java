/* 
 * Copyright 2013-2014 Ivan Trendafilov and contributors
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

package ivantrendafilov_confucius._97.mocks;

import ivantrendafilov_confucius._97.misuse.AbstractConfiguration;

import java.io.InputStream;

/**
 * misuse版AbstractConfigurationをテスト可能にするための具象サブクラス。
 */
public class InjectableConfigurationMisuse extends AbstractConfiguration {

    public InjectableConfigurationMisuse() {
        super();
    }

    public InjectableConfigurationMisuse(String filePath, String context) {
        super(filePath, context);
    }

    public InjectableConfigurationMisuse(InputStream inputStream, String context) {
        super(inputStream, context);
    }
}
