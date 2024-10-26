package backend.academy.loganalyzer.error.reading;

import backend.academy.loganalyzer.error.LogAnalyzerException;

/**
 * Exception for errors related to log reading.
 * <p>
 * This exception is used to handle errors that occur during the
 * process of reading logs. It extends LogAnalyzerException,
 * providing constructors to specify error messages and causes.
 * </p>
 */
public class LogReadingException extends LogAnalyzerException {
    /**
     * Constructs a new LogReadingException with the specified detail message.
     *
     * @param message the detail message
     */
    public LogReadingException(String message) {
        super(message);
    }

    /**
     * Constructs a new LogReadingException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method)
     */
    public LogReadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
