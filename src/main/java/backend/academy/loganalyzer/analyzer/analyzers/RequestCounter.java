package backend.academy.loganalyzer.analyzer.analyzers;

import backend.academy.loganalyzer.model.LogEntry;
import java.util.List;

/**
 * The RequestCounter class is responsible for counting the total number of requests
 * from a list of log entries.
 */
public class RequestCounter {

    /**
     * Counts the total number of requests present in the given list of log entries.
     *
     * @param logEntries The list of log entries to analyze.
     * @return The total number of requests.
     */
    public int countTotalRequests(List<LogEntry> logEntries) {
        return logEntries.size();
    }
}
