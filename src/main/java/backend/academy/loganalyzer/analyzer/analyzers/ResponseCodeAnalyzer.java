package backend.academy.loganalyzer.analyzer.analyzers;

import backend.academy.loganalyzer.model.LogEntry;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The ResponseCodeAnalyzer class is responsible for analyzing the frequency of HTTP response codes
 * from a list of log entries.
 */
public class ResponseCodeAnalyzer {

    /**
     * Retrieves the most common HTTP response codes and their frequency.
     *
     * @param logEntries The list of log entries to analyze.
     * @return A map where the key is the HTTP response code, and the value is the count of occurrences
     *         of that response code.
     */
    public Map<Integer, Long> getMostCommonResponseCodes(List<LogEntry> logEntries) {
        return logEntries.stream()
            .collect(Collectors.groupingBy(LogEntry::statusCode, Collectors.counting()));
    }
}
