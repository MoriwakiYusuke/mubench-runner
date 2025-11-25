## Instruction
You are a software engineer specializing in REST API.
Use the guidelines below to make any necessary modifications.

### Modification Procedure
0. First, familiarise yourself with the following steps and ### Notes.
1. Check the technical specifications of the Java API that you have studied or in the official documentation. If you don't know, output the ### Input Code as it is.
2. Based on the technical specifications of the Java API you have reviewed in step 1, identify the code according to the deprecated specifications contained in the ### Input Code. In this case, the deprecated specifications are the Java API calls that have been deprecated. If no code according to the deprecated specification is found, identify code that is not based on best practice. If you are not sure, output the ### Input Code as it is.
3. If you find code according to the deprecated specification or not based on best practice in step 2, check the technical specifications in the Java API that you have studied or in the official documentation. If you are not sure, output the ### Input Code as it is.
4. With attention to the points listed in ### Notes below, modify the code identified in step 2 to follow the recommended specification analysed in step 3.
5. Verify again that the modified code works correctly.
6. If you determine that it works correctly, output the modified code.
7. If it is judged to fail, output the ### Input Code as it is.
8. If you are not sure, output the ### Input Code as it is.

### Notes.
- You must follow the ## Context.

## Input Code
```java
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

package org.trendafilov.confucius.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trendafilov.confucius.Configurable;

import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;

public abstract class AbstractConfiguration implements Configurable {
	private final static Logger LOG = LoggerFactory.getLogger(AbstractConfiguration.class);

	private final static String ITEM_SEPARATOR = ",";
	protected static String FILE_PARAM = "conf.properties";
	protected static String CONTEXT_PARAM = "conf.context";

	private final ConfigurationDataProvider configurationDataProvider;
	private final String context;
	private final Map<String, String> initialState;

	public AbstractConfiguration() {
		this.configurationDataProvider = new FileConfigurationDataProvider(System.getProperty(FILE_PARAM));
		this.context = System.getProperty(CONTEXT_PARAM);
		this.initialState = Collections.unmodifiableMap(Utils.propertiesToMap(System.getProperties()));
		init();
	}

	public AbstractConfiguration(String filePath, String context) {
		if (filePath == null)
			throw new ConfigurationException("filePath cannot be null. Use no arg constructor instead.");
		if (context != null)
			setProperty(CONTEXT_PARAM, context);
		setProperty(FILE_PARAM, filePath);
		this.configurationDataProvider = new FileConfigurationDataProvider(filePath);
		this.context = context;
		this.initialState = Collections.unmodifiableMap(Utils.propertiesToMap(System.getProperties()));
		init();
	}

	public AbstractConfiguration(InputStream inputStream, String context) {
		this.configurationDataProvider = new StreamConfigurationDataProvider(inputStream);
		this.context = context;
		this.initialState = Collections.unmodifiableMap(Utils.propertiesToMap(System.getProperties()));
		init();
	}

	private void init() {
		LOG.info("Initializing configuration...");
		setProperties(initialState);
		setProperties(new Parser(configurationDataProvider, context).getConfiguration());
	}

	public synchronized Set<String> keySet() {
		return Utils.propertiesToMap(getProperties()).keySet();
	}

	public boolean getBooleanValue(String key) {
		return Boolean.parseBoolean(getKey(key));
	}

	public synchronized boolean getBooleanValue(String key, boolean defaultValue) {
		String value = System.getProperty(key);
		return value == null ? defaultValue : Boolean.parseBoolean(value);
	}

	public List<Boolean> getBooleanList(String key, String separator) {
		List<Boolean> parts = new ArrayList<>();
		for (String value : getKey(key).split(separator))
			parts.add(Boolean.parseBoolean(value.trim()));
		return parts;
	}

	public List<Boolean> getBooleanList(String key) {
		return getBooleanList(key, ITEM_SEPARATOR);
	}

	public byte getByteValue(String key) {
		return Byte.parseByte(getKey(key));
	}

	public synchronized byte getByteValue(String key, byte defaultValue) {
		String value = System.getProperty(key);
		return value == null ? defaultValue : Byte.parseByte(value);
	}

	public List<Byte> getByteList(String key, String separator) {
		List<Byte> parts = new ArrayList<>();
		for (String value : getKey(key).split(separator))
			parts.add(Byte.parseByte(value.trim()));
		return parts;
	}

	public List<Byte> getByteList(String key) {
		return getByteList(key, ITEM_SEPARATOR);
	}

	public char getCharValue(String key) {
		return getKey(key).charAt(0);
	}

	public synchronized char getCharValue(String key, char defaultValue) {
		String value = System.getProperty(key);
		return value == null ? defaultValue : value.charAt(0);
	}

	public List<Character> getCharList(String key, String separator) {
		List<Character> parts = new ArrayList<>();
		for (String value : getKey(key).split(separator))
			parts.add(value.trim().charAt(0));
		return parts;
	}

	public List<Character> getCharList(String key) {
		return getCharList(key, ITEM_SEPARATOR);
	}

	public double getDoubleValue(String key) {
		return Double.parseDouble(getKey(key));
	}

	public synchronized double getDoubleValue(String key, double defaultValue) {
		String value = System.getProperty(key);
		return value == null ? defaultValue : Double.parseDouble(value);
	}

	public List<Double> getDoubleList(String key, String separator) {
		List<Double> parts = new ArrayList<>();
		for (String value : getKey(key).split(separator))
			parts.add(Double.parseDouble(value.trim()));
		return parts;
	}

	public List<Double> getDoubleList(String key) {
		return getDoubleList(key, ITEM_SEPARATOR);
	}

	public float getFloatValue(String key) {
		return Float.parseFloat(getKey(key));
	}

	public synchronized float getFloatValue(String key, float defaultValue) {
		String value = System.getProperty(key);
		return value == null ? defaultValue : Float.parseFloat(value);
	}

	public List<Float> getFloatList(String key, String separator) {
		List<Float> parts = new ArrayList<>();
		for (String value : getKey(key).split(separator))
			parts.add(Float.parseFloat(value.trim()));
		return parts;
	}

	public List<Float> getFloatList(String key) {
		return getFloatList(key, ITEM_SEPARATOR);
	}

	public int getIntValue(String key) {
		return Integer.parseInt(getKey(key));
	}

	public synchronized int getIntValue(String key, int defaultValue) {
		String value = System.getProperty(key);
		return value == null ? defaultValue : Integer.parseInt(value);
	}

	public List<Integer> getIntList(String key, String separator) {
		List<Integer> parts = new ArrayList<>();
		for (String value : getKey(key).split(separator))
			parts.add(Integer.parseInt(value.trim()));
		return parts;
	}

	public List<Integer> getIntList(String key) {
		return getIntList(key, ITEM_SEPARATOR);
	}

	public long getLongValue(String key) {
		return Long.parseLong(getKey(key));
	}

	public synchronized long getLongValue(String key, long defaultValue) {
		String value = System.getProperty(key);
		return value == null ? defaultValue : Long.parseLong(value);
	}

	public List<Long> getLongList(String key, String separator) {
		List<Long> parts = new ArrayList<>();
		for (String value : getKey(key).split(separator))
			parts.add(Long.parseLong(value.trim()));
		return parts;
	}

	public List<Long> getLongList(String key) {
		return getLongList(key, ITEM_SEPARATOR);
	}

	public short getShortValue(String key) {
		return Short.parseShort(getKey(key));
	}

	public synchronized short getShortValue(String key, short defaultValue) {
		String value = System.getProperty(key);
		return value == null ? defaultValue : Short.parseShort(value);
	}

	public List<Short> getShortList(String key, String separator) {
		List<Short> parts = new ArrayList<>();
		for (String value : getKey(key).split(separator))
			parts.add(Short.parseShort(value.trim()));
		return parts;
	}

	public List<Short> getShortList(String key) {
		return getShortList(key, ITEM_SEPARATOR);
	}

	public String getStringValue(String key) {
		return getKey(key);
	}

	public synchronized String getStringValue(String key, String defautValue) {
		String value = System.getProperty(key);
		return value == null ? defautValue : value;
	}

	public List<String> getStringList(String key, String separator) {
		List<String> parts = new ArrayList<>();
		for (String value : getKey(key).split(separator))
			parts.add(value.trim());
		return parts;
	}

	public List<String> getStringList(String key) {
		return getStringList(key, ITEM_SEPARATOR);
	}

	public synchronized Properties getProperties() {
		return System.getProperties();
	}

	public synchronized <T> void setProperty(String key, T value) {
		String item = value.toString();
		System.setProperty(key, item);
		LOG.info("Set configuration property: [{}] => [{}]", key, item);
	}

	public synchronized <T> void setProperties(Map<String, T> properties) {
		for (Entry<String, T> entry : properties.entrySet())
			setProperty(entry.getKey(), entry.getValue());
	}

	public synchronized void setProperties(Properties properties) {
		for (Object e : properties.keySet())
			setProperty((String) e, properties.getProperty((String) e));
	}

	public synchronized void clearProperty(String key) {
		System.clearProperty(key);
		LOG.info("Unset configuration property: [{}]", key);
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * <p>
	 * The reset procedure restores configuration properties to their initial
	 * values at the time of creation of the <tt>Configurable</tt> instance.
	 * Configuration properties specified via a file are re-processed.
	 * </p>
	 */
	public synchronized void reset() {
		for (String key : Utils.propertiesToMap(getProperties()).keySet())
			clearProperty(key);
		init();
		LOG.info("Configuration properties have been reset");
	}

	private synchronized String getKey(String key) {
		String value = System.getProperty(key);
		if (value == null)
			throw new ConfigurationException(String.format("Unable to find configuration value for key [%s]", key));
		return value;
	}
}
```

## Context

**Bug Location**: File `org/trendafilov/confucius/core/AbstractConfiguration.java`, Method `getLongList(String, String)`
**Bug Type**: missing/exception_handling - `AbstractConfiguration.java` calls `java.lang.Long.parseLong` without first checking whether the argument parses. This leads to an uncaught `NumberFormatException`.

Can you identify and fix it?

## Output Indicator
Update the ### Input Code as per the latest API specification, making necessary modifications.
Ensure the structure and format remain as close as possible to the original, but deprecated code must be updated. Output the all revised code without additional explanations or comments.
