package backend.academy.loganalyzer.error.parsing.argument;

import backend.academy.loganalyzer.error.parsing.LogParsingException;

/**
 * Exception thrown when an invalid argument is encountered during log parsing.
 * <p>
 * This exception is used to signal that an argument provided to a log parsing
 * operation is invalid. It extends LogParsingException, allowing for specific
 * error messages and causes to be specified.
 * </p>
 */
public class InvalidArgumentException extends LogParsingException {
    public InvalidArgumentException(String message) {
        super(message);
    }

    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
