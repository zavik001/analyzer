package backend.academy.loganalyzer.analyzer.analyzers;

import backend.academy.loganalyzer.model.LogEntry;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The PopularResourceAnalyzer class is responsible for identifying the most frequently requested resources
 * from a list of log entries.
 */
public class PopularResourceAnalyzer {
    private static final int TOP = 5;

    /**
     * Retrieves the top most popular resources based on the number of requests.
     *
     * @param logEntries The list of log entries to analyze.
     * @return A list of the most popular resources, limited to the top {@value #TOP}.
     */
    public List<String> getMostPopularResources(List<LogEntry> logEntries) {
        Map<String, Long> resourceCount = logEntries.stream()
            .collect(Collectors.groupingBy(LogEntry::requestPath, Collectors.counting()));

        return resourceCount.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .map(Map.Entry::getKey)
            .limit(TOP)
            .collect(Collectors.toList());
    }
}
