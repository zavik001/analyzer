package backend.academy.loganalyzer.analyzer.analyzers;

import backend.academy.loganalyzer.model.LogEntry;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The RequestFrequencyAnalyzer class is responsible for analyzing the frequency of requests
 * by grouping them based on the hour in which they occurred.
 */
public class RequestFrequencyAnalyzer {

    private static final DateTimeFormatter HOUR_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

    /**
     * Counts the number of requests per hour from the given list of log entries.
     *
     * @param logEntries The list of log entries to analyze.
     * @return A map where the key is the formatted hour (e.g., "yyyy-MM-dd HH"), and the value
     *         is the count of requests that occurred during that hour.
     */
    public Map<String, Long> countRequestsByHour(List<LogEntry> logEntries) {
        return logEntries.stream()
            .collect(Collectors.groupingBy(logEntry -> {
                LocalDateTime timestamp = LocalDateTime.parse(logEntry.timestamp(),
                    DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z"));
                return timestamp.format(HOUR_FORMATTER);
            }, Collectors.counting()));
    }
}
