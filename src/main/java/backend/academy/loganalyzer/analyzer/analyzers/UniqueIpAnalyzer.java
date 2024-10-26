package backend.academy.loganalyzer.analyzer.analyzers;

import backend.academy.loganalyzer.model.LogEntry;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The UniqueIpAnalyzer class is responsible for counting the number of unique IP addresses
 * found in a list of log entries.
 */
public class UniqueIpAnalyzer {

    /**
     * Counts the unique IP addresses present in the provided log entries.
     *
     * @param logEntries The list of log entries to analyze.
     * @return The number of unique IP addresses.
     */
    public int countUniqueIps(List<LogEntry> logEntries) {
        Set<String> uniqueIps = logEntries.stream()
            .map(LogEntry::ipAddress)
            .collect(Collectors.toSet());
        return uniqueIps.size();
    }
}
