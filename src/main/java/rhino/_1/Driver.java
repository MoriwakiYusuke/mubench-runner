package rhino._1;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rhino._1.mocks.CompilerEnvirons;
import rhino._1.mocks.DefaultErrorReporter;
import rhino._1.mocks.ErrorReporter;
import rhino._1.mocks.ScriptOrFnNode;

/**
 * Driver for rhino Parser.
 * Covers all public methods/constructors via reflection:
 * - Parser(CompilerEnvirons, ErrorReporter)
 * - getEncodedSource()
 * - eof()
 * - parse(String, String, int)
 * - parse(Reader, String, int)
 */
public class Driver {
    
    private final Object parser;
    private final Class<?> parserClass;
    private final String variant;
    
    // For static analysis (kept for compatibility)
    private final String sourceCode;
    private final String functionMethodBody;
    
    /**
     * Constructor with variant name.
     */
    public Driver(String variant) throws Exception {
        this.variant = variant;
        
        // Reflection-based instantiation
        String className = "rhino._1." + variant + ".Parser";
        this.parserClass = Class.forName(className);
        Constructor<?> ctor = parserClass.getConstructor(CompilerEnvirons.class, ErrorReporter.class);
        this.parser = ctor.newInstance(new CompilerEnvirons(), new DefaultErrorReporter());
        
        // Static analysis (kept for backward compatibility)
        Path parserPath = Path.of("src/main/java/rhino/_1", variant, "Parser.java");
        this.sourceCode = Files.readString(parserPath);
        this.functionMethodBody = extractFunctionMethod(sourceCode);
    }
    
    /**
     * Constructor with variant name and custom environment/reporter.
     */
    public Driver(String variant, CompilerEnvirons env, ErrorReporter reporter) throws Exception {
        this.variant = variant;
        
        // Reflection-based instantiation
        String className = "rhino._1." + variant + ".Parser";
        this.parserClass = Class.forName(className);
        Constructor<?> ctor = parserClass.getConstructor(CompilerEnvirons.class, ErrorReporter.class);
        this.parser = ctor.newInstance(env, reporter);
        
        // Static analysis (kept for backward compatibility)
        Path parserPath = Path.of("src/main/java/rhino/_1", variant, "Parser.java");
        this.sourceCode = Files.readString(parserPath);
        this.functionMethodBody = extractFunctionMethod(sourceCode);
    }
    
    // ========== Public method wrappers (reflection-based) ==========
    
    /**
     * Wrapper for Parser.getEncodedSource()
     */
    public String getEncodedSource() throws Exception {
        Method method = parserClass.getMethod("getEncodedSource");
        return (String) method.invoke(parser);
    }
    
    /**
     * Wrapper for Parser.eof()
     */
    public boolean eof() throws Exception {
        Method method = parserClass.getMethod("eof");
        return (Boolean) method.invoke(parser);
    }
    
    /**
     * Wrapper for Parser.parse(String, String, int)
     */
    public ScriptOrFnNode parse(String sourceString, String sourceURI, int lineno) throws Exception {
        Method method = parserClass.getMethod("parse", String.class, String.class, int.class);
        return (ScriptOrFnNode) method.invoke(parser, sourceString, sourceURI, lineno);
    }
    
    /**
     * Wrapper for Parser.parse(Reader, String, int)
     */
    public ScriptOrFnNode parse(Reader sourceReader, String sourceURI, int lineno) throws Exception {
        Method method = parserClass.getMethod("parse", Reader.class, String.class, int.class);
        return (ScriptOrFnNode) method.invoke(parser, sourceReader, sourceURI, lineno);
    }
    
    /**
     * Convenience method to parse from string using Reader variant
     */
    public ScriptOrFnNode parseFromReader(String source, String sourceURI, int lineno) throws Exception {
        return parse(new StringReader(source), sourceURI, lineno);
    }
    
    // ========== Static analysis methods (for initFunction detection) ==========
    
    private String extractFunctionMethod(String source) {
        Pattern pattern = Pattern.compile(
            "private\\s+Node\\s+function\\s*\\(\\s*int\\s+functionType\\s*\\)",
            Pattern.DOTALL
        );
        Matcher matcher = pattern.matcher(source);
        if (!matcher.find()) {
            return "";
        }
        int start = matcher.start();
        int braceCount = 0;
        int methodStart = -1;
        for (int i = start; i < source.length(); i++) {
            char c = source.charAt(i);
            if (c == '{') {
                if (methodStart == -1) methodStart = i;
                braceCount++;
            } else if (c == '}') {
                braceCount--;
                if (braceCount == 0 && methodStart != -1) {
                    return source.substring(start, i + 1);
                }
            }
        }
        return "";
    }
    
    public String getFunctionMethodBody() {
        return functionMethodBody;
    }
    
    public int countInitFunctionCalls() {
        Pattern pattern = Pattern.compile("nf\\.initFunction\\s*\\(");
        Matcher matcher = pattern.matcher(functionMethodBody);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
    
    public boolean hasCorrectInitFunctionPattern() {
        return countInitFunctionCalls() == 1;
    }
    
    public boolean hasDuplicateInitFunctionCall() {
        return countInitFunctionCalls() > 1;
    }
}
