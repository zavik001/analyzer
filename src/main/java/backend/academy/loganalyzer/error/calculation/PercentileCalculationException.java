package backend.academy.loganalyzer.error.calculation;

/**
 * Exception for errors related to percentile calculation.
 * <p>
 * This exception is used to handle cases where an error occurs during the
 * calculation of percentiles. It extends CalculateException, allowing for
 * specific error messages and causes to be provided.
 * </p>
 */
public class PercentileCalculationException extends CalculateException {
    public PercentileCalculationException(String message) {
        super(message);
    }

    public PercentileCalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}
