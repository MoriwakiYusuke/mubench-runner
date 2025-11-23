## Instruction
You are an expert QA Engineer specializing in Java security testing.
Your task is to write a **JUnit 5 test case** that reproduces a specific vulnerability/bug.

The test must meet the following criteria:
1. **PASS** (Success) when run against the **Original Code** (Correct Implementation).
2. **FAIL** (Assertion Error) when run against the **Misuse Code** (Vulnerable Implementation).

## Context & Constraints
- The test will be inserted into a pre-defined test class named `CommonLogic`.
- You **MUST** use the provided wrapper class `SourceDriver` to interact with the target code.
- **Do NOT** instantiate the target classes (e.g., `new Secure()`) directly.
- Always obtain the instance using: `SourceDriver driver = getTargetDriver();`.
- Output **only** the Java method code (starting with `@Test`). Do not include class declarations, imports, or markdown explanations.


## Procedure
Follow these steps to generate the test case:

1.  **Analyze the Bug Description:**
    Understand the affected class/method and the root cause of the bug (e.g., missing encoding, lack of validation, logic error).

2.  **Examine the Misuse Code (Vulnerable):**
    Identify the specific lines in the Misuse Code that contain the vulnerability. Understand the mechanism that causes it to produce incorrect results (e.g., corrupted text, exceptions, wrong values) given specific inputs.

3.  **Examine the Original Code (Fixed):**
    Review the Original Code to confirm how the vulnerability was resolved. Ensure you understand the expected correct behavior for the same input.

4.  **Design the Reproduction Test:**
    Formulate a test logic that meets the following strict criteria:
    * **PASS** (Success) when run against the **Original Code**.
    * **FAIL** (AssertionError or Exception) when run against the **Misuse Code**.
    * **Critical:** Select specific input data (e.g., multi-byte characters, boundary values, special symbols) that triggers the bug described in the analysis.

5.  **Implement the Test Code:**
    Write the Java method using **only** the provided `SourceDriver` API to interact with the target.



## Reference: SourceDriver API

```java
public class SourceDriver {
    
    private final SecureInterface target;

    // コンストラクタで「操作対象の実体」を受け取る
    public SourceDriver(SecureInterface target) {
        this.target = target;
    }

    // String メソッド (既存)
    public String encrypt(String value) {
        return target.encrypt(value);
    }

    public String decrypt(String value) {
        return target.decrypt(value);
    }

    public String getDigest(String value) {
        return target.getDigest(value);
    }
    
    // 【追加部分】Integer 型のメソッド
    public Integer encrypt(Integer value) {
        return target.encrypt(value);
    }
    
    public Integer decrypt(Integer value) {
        return target.decrypt(value);
    }

    // 【追加部分】BigDecimal 型のメソッド
    public BigDecimal encrypt(BigDecimal value) {
        return target.encrypt(value);
    }
    
    public BigDecimal decrypt(BigDecimal value) {
        return target.decrypt(value);
    }

    // 【追加部分】Timestamp 型のメソッド
    public Timestamp encrypt(Timestamp value) {
        return target.encrypt(value);
    }
    
    public Timestamp decrypt(Timestamp value) {
        return target.decrypt(value);
    }
    
    // 【追加部分】isDigest メソッド
    public boolean isDigest(String value) {
        return target.isDigest(value);
    }
}
```


## Input Data

### Bug Description

```yml
api:
- java.lang.String
violations:
- missing/call
- redundant/call
crash: false
description: >
  A string is converted to bytes without specifying an explicit encoding.
  The bytes are then passed to Cipher.doFinal(). The fix specifies the encoding "UTF-8".
location:
  file: org/compiere/util/Secure.java
  method: encrypt(String)
fix:
  commit: https://sourceforge.net/p/adempiere/svn/1312/tree/trunk/looks/src/org/compiere/util/Secure.java?diff=5139a2ef34309d2ec1827857:1311
  before: https://sourceforge.net/p/adempiere/svn/1311/tree/trunk/looks/src/org/compiere/util/Secure.java#l1
  after: https://sourceforge.net/p/adempiere/svn/1312/tree/trunk/looks/src/org/compiere/util/Secure.java
  revision: 1312
internal: false
pattern:
- multiple objects
source:
  name: SourceForge
```




### Original Code (Correct Implementation)

```java
```

### Misuse Code (Vulnerable Implementation)

```java
```


「単一のテスト」という制約を外し、**「複数のテストメソッドを作成して、あらゆるパターンを網羅する」** ような指示に変更しました。

これにより、LLMは基本パターンの再現だけでなく、エッジケースや境界値分析などを含めた複数のテストケースを提案するようになります。

-----

## Output Indicator

Output **multiple** JUnit 5 test methods to comprehensively verify the security fix.
Break down the verification logic into separate, granular test methods to cover different scenarios (e.g., standard input, multi-byte characters, boundary values, empty strings).

**Crucial:**

  * **Granularity:** Each test method should focus on a specific aspect of the vulnerability or a specific input type.
  * **Correctness:** For every test, verify the functional correctness (e.g., successful round-trip encryption/decryption).
  * **Independence:** Each test method must be self-contained and not rely on the state of other tests.

Example format:

```java
@Test
@DisplayName("Base Case: Verify standard functionality")
void testStandardBehavior() {
    SourceDriver driver = getTargetDriver();
    // Use a standard, valid input typical for the target type
    String input = "standard_valid_input"; 
    String result = driver.encrypt(input);
    
    // Verify correct logic (Round-trip)
    assertEquals(input, driver.decrypt(result), "Standard functionality failed");
}

@Test
@DisplayName("Reproduction: Verify fix for reported vulnerability")
void testVulnerabilityReproduction() {
    SourceDriver driver = getTargetDriver();
    // CRITICAL: Use input explicitly derived from the 'Bug Description'
    // (e.g., multi-byte chars, specific large numbers, or format that caused the bug)
    String triggerInput = "input_that_triggers_the_bug"; 
    String result = driver.encrypt(triggerInput);
    
    // Assertion should pass on Fixed Code and fail on Misuse Code
    assertEquals(triggerInput, driver.decrypt(result), "Fix verification failed for specific bug input");
}

@Test
@DisplayName("Edge Case: Verify boundary handling")
void testBoundaryCondition() {
    SourceDriver driver = getTargetDriver();
    // Use boundary values (e.g., empty string, 0, -1, max_value)
    String edgeInput = ""; 
    String result = driver.encrypt(edgeInput);
    
    assertEquals(edgeInput, driver.decrypt(result), "Edge case handling failed");
}
```