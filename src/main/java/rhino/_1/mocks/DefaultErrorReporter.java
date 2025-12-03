package rhino._1.mocks;

/**
 * Default implementation of ErrorReporter that does nothing.
 * Used for testing when error handling behavior is not relevant.
 */
public class DefaultErrorReporter implements ErrorReporter {
    
    @Override
    public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
        // No-op
    }
    
    @Override
    public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
        // No-op
    }
    
    @Override
    public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset) {
        return new EvaluatorException(message);
    }
}
