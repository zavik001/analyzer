package backend.academy.loganalyzer.error.reading.path;

import backend.academy.loganalyzer.error.reading.LogReadingException;

/**
 * Exception thrown when an invalid path is provided.
 * <p>
 * This exception is used to handle cases where a path is invalid during
 * the log reading process. It extends LogReadingException, allowing for
 * specific error messages and causes to be specified.
 * </p>
 */
public class InvalidPathException extends LogReadingException {
    /**
     * Constructs a new InvalidPathException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidPathException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidPathException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method)
     */
    public InvalidPathException(String message, Throwable cause) {
        super(message, cause);
    }
}
