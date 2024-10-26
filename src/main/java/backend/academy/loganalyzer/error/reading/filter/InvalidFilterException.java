package backend.academy.loganalyzer.error.reading.filter;

import backend.academy.loganalyzer.error.reading.LogReadingException;

/**
 * Exception for errors related to filtering.
 * <p>
 * This exception is used to handle cases where an error occurs during
 * the filtering process. It extends LogReadingException, allowing for
 * specific error messages and causes to be provided.
 * </p>
 */
public class InvalidFilterException extends LogReadingException {
    public InvalidFilterException(String message) {
        super(message);
    }

    public InvalidFilterException(String message, Throwable cause) {
        super(message, cause);
    }
}
