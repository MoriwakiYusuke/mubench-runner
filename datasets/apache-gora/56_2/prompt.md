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
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.gora.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

import org.apache.gora.util.WritableUtils;
import org.junit.Test;

/**
 * Test case for {@link WritableUtils} class.
 */
public class TestWritableUtils {
  @Test
  public void testWritesReads() throws Exception {
    Properties props = new Properties();
    props.put("keyBlah", "valueBlah");
    props.put("keyBlah2", "valueBlah2");

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    DataOutput out = new DataOutputStream(bos);
    WritableUtils.writeProperties(out, props);


    DataInput in = new DataInputStream(new ByteArrayInputStream(bos.toByteArray()));

    Properties propsRead = WritableUtils.readProperties(in);

    assertEquals(propsRead.get("keyBlah"), props.get("keyBlah"));
    assertEquals(propsRead.get("keyBlah2"), props.get("keyBlah2"));
  }
}
```

## Context
In this class, the method 'public void testWritesReads() throws Exception' has the following issue 'When a DataOutputStream instance wraps an underlying ByteArrayOutputStream instance, it is recommended to flush or close the DataOutputStream before invoking the underlying instances's toByteArray(). Also, it is a good practice to call flush/close explicitly as mentioned for example [here](http://stackoverflow.com/questions/2984538/how-to-use-bytearrayoutputstream-and-dataoutputstream-simultaneously-java). This pull request add a flush method before toByteArray.'.
Can you idenfity and /fix it?

## Output Indicator
Update the ### Input Code as per the latest API specification, making necessary modifications.
Ensure the structure and format remain as close as possible to the original, but deprecated code must be updated. Output the all revised code without additional explanations or comments.