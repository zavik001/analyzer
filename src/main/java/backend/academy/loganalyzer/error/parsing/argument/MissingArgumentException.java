package backend.academy.loganalyzer.error.parsing.argument;

import backend.academy.loganalyzer.error.parsing.LogParsingException;

/**
 * Exception thrown when a required argument is missing during log parsing.
 * <p>
 * This exception is used to signal that a necessary argument for a log parsing
 * operation is missing. It extends LogParsingException, allowing for specific
 * error messages and causes to be specified.
 * </p>
 */
public class MissingArgumentException extends LogParsingException {
    public MissingArgumentException(String message) {
        super(message);
    }

    public MissingArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
