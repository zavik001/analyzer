package backend.academy.loganalyzer.error.writer;

/**
 * Exception thrown when the format of the output file is invalid.
 */
public class InvalidFormatException extends LogWritingException {
    public InvalidFormatException(String message) {
        super(message);
    }

    public InvalidFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
