package backend.academy.loganalyzer.error.parsing;

import backend.academy.loganalyzer.error.LogAnalyzerException;

/**
 * Exception for errors related to log parsing.
 * <p>
 * This exception is used to handle errors that occur during the
 * process of parsing logs. It extends LogAnalyzerException,
 * providing constructors to specify error messages and causes.
 * </p>
 */
public class LogParsingException extends LogAnalyzerException {
    /**
     * Constructs a new exception with the specified message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     */
    public LogParsingException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified message and cause.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A {@code null} value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     */
    public LogParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
