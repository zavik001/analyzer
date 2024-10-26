package backend.academy.loganalyzer.analyzer.analyzers;

import backend.academy.loganalyzer.error.calculation.PercentileCalculationException;
import backend.academy.loganalyzer.model.LogEntry;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * The PercentileCalculator class is responsible for calculating a specific percentile of response sizes
 * from a list of log entries.
 */
@Slf4j
public class PercentileCalculator {
    private static final int MAX_PERCENTILE = 100;
    private static final double PERCENT_DIVISOR = 100.0;

    /**
     * Calculates the specified percentile of response sizes from the given list of log entries.
     *
     * @param logEntries The list of log entries to analyze.
     * @param percentile The desired percentile to calculate (must be between 0 and 100).
     * @return The response size value at the specified percentile.
     * @throws PercentileCalculationException If the percentile value is not within the valid range.
     */
    public double calculatePercentile(List<LogEntry> logEntries, int percentile)
        throws PercentileCalculationException {

        if (percentile < 0 || percentile > MAX_PERCENTILE) {
            throw new PercentileCalculationException(
                "Invalid percentile value. It must be in the range of 0 to 100.");
        }

        if (logEntries.isEmpty()) {
            log.warn("The log list is empty. Unable to calculate the percentile");
            return 0.0;
        }

        List<Integer> sortedSizes = logEntries.stream()
            .map(LogEntry::responseSize)
            .sorted()
            .collect(Collectors.toList());

        int index = (int) Math.ceil(percentile / PERCENT_DIVISOR * sortedSizes.size()) - 1;
        index = Math.min(Math.max(0, index), sortedSizes.size() - 1);

        return sortedSizes.get(index);
    }
}
