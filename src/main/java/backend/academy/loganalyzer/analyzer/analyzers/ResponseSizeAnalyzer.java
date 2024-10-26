package backend.academy.loganalyzer.analyzer.analyzers;

import backend.academy.loganalyzer.model.LogEntry;
import java.util.List;

/**
 * The ResponseSizeAnalyzer class is responsible for calculating the average size of server responses
 * from a list of log entries.
 */
public class ResponseSizeAnalyzer {

    /**
     * Calculates the average size of server responses from the provided log entries.
     *
     * @param logEntries The list of log entries to analyze.
     * @return The average response size. Returns 0.0 if there are no entries.
     */
    public double calculateAverageSize(List<LogEntry> logEntries) {
        return logEntries.stream()
            .mapToInt(LogEntry::responseSize)
            .average()
            .orElse(0.0);
    }
}
