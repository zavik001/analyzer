package backend.academy.loganalyzer.error.calculation;

import backend.academy.loganalyzer.error.LogAnalyzerException;

/**
 * Base exception for errors related to calculations.
 * <p>
 * This exception is used to handle errors that occur during the
 * process of calculating metrics. It extends LogAnalyzerException,
 * providing constructors to specify error messages and causes.
 * </p>
 */
public class CalculateException extends LogAnalyzerException {
    public CalculateException(String message) {
        super(message);
    }

    public CalculateException(String message, Throwable cause) {
        super(message, cause);
    }
}
