package backend.academy.loganalyzer.error.writer;

import backend.academy.loganalyzer.error.LogAnalyzerException;

/**
 * Base exception for errors that occurred during log writing.
 */
public class LogWritingException extends LogAnalyzerException {
    /**
     * Constructor with error message.
     *
     * @param message error message.
     */
    public LogWritingException(String message) {
        super(message);
    }

    /**
     * Constructor with error message and cause.
     *
     * @param message error message.
     * @param cause   cause of the exception.
     */
    public LogWritingException(String message, Throwable cause) {
        super(message, cause);
    }
}
