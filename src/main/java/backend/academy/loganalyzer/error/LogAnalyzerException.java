package backend.academy.loganalyzer.error;

/**
 * Base exception for the log analyzer.
 * <p>
 * This exception is used within the log analyzer system to handle
 * all errors related to log analysis. It extends RuntimeException,
 * allowing for the omission of explicit declaration in methods.
 * </p>
 */
public class LogAnalyzerException extends RuntimeException {
    /**
     * Constructs a new LogAnalyzerException with the specified detail message.
     *
     * @param message the detail message
     */
    public LogAnalyzerException(String message) {
        super(message);
    }

    /**
     * Constructs a new LogAnalyzerException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method)
     */
    public LogAnalyzerException(String message, Throwable cause) {
        super(message, cause);
    }
}
