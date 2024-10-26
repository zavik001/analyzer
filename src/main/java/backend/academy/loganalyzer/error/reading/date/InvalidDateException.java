package backend.academy.loganalyzer.error.reading.date;

import backend.academy.loganalyzer.error.reading.LogReadingException;

/**
 * Exception for errors related to date formats.
 * <p>
 * This exception is used to handle cases where an error occurs during
 * the date parsing process. It extends LogReadingException, allowing for
 * specific error messages and causes to be provided.
 * </p>
 */
public class InvalidDateException extends LogReadingException {
    public InvalidDateException(String message) {
        super(message);
    }

    public InvalidDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
