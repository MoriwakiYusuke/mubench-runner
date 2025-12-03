package rhino._1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Driver {
    
    private final String sourceCode;
    private final String functionMethodBody;
    
    public Driver(String variant) throws IOException {
        Path parserPath = Path.of("src/main/java/rhino/_1", variant, "Parser.java");
        this.sourceCode = Files.readString(parserPath);
        this.functionMethodBody = extractFunctionMethod(sourceCode);
    }
    
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
