package rhino._1.mocks;

public interface ErrorReporter {
    void warning(String message, String sourceName, int line, String lineSource, int lineOffset);
    void error(String message, String sourceName, int line, String lineSource, int lineOffset);
    EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset);
}
