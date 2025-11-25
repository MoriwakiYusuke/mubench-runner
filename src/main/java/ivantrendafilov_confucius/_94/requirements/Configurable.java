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

package ivantrendafilov_confucius._94.requirements;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public interface Configurable {
	Set<String> keySet();
	boolean getBooleanValue(String key);
	boolean getBooleanValue(String key, boolean defaultValue);
	List<Boolean> getBooleanList(String key, String separator);
	List<Boolean> getBooleanList(String key);
	byte getByteValue(String key);
	byte getByteValue(String key, byte defaultValue);
	List<Byte> getByteList(String key, String separator);
	List<Byte> getByteList(String key);
	char getCharValue(String key);
	char getCharValue(String key, char defaultValue);
	List<Character> getCharList(String key, String separator);
	List<Character> getCharList(String key);
	double getDoubleValue(String key);
	double getDoubleValue(String key, double defaultValue);
	List<Double> getDoubleList(String key, String separator);
	List<Double> getDoubleList(String key);
	float getFloatValue(String key);
	float getFloatValue(String key, float defaultValue);
	List<Float> getFloatList(String key, String separator);
	List<Float> getFloatList(String key);
	int getIntValue(String key);
	int getIntValue(String key, int defaultValue);
	List<Integer> getIntList(String key, String separator);
	List<Integer> getIntList(String key);
	long getLongValue(String key);
	long getLongValue(String key, long defaultValue);
	List<Long> getLongList(String key, String separator);
	List<Long> getLongList(String key);
	short getShortValue(String key);
	short getShortValue(String key, short defaultValue);
	List<Short> getShortList(String key, String separator);
	List<Short> getShortList(String key);
	String getStringValue(String key);
	String getStringValue(String key, String defautValue);
	List<String> getStringList(String key, String separator);
	List<String> getStringList(String key);
	Properties getProperties();
	<T> void setProperty(String key, T value);
	<T> void setProperties(Map<String, T> properties);
	void setProperties(Properties properties);
	void clearProperty(String key);
	void reset();
}
