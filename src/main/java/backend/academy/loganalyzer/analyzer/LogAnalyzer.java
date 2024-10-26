package backend.academy.loganalyzer.analyzer;

import backend.academy.loganalyzer.analyzer.analyzers.PercentileCalculator;
import backend.academy.loganalyzer.analyzer.analyzers.PopularResourceAnalyzer;
import backend.academy.loganalyzer.analyzer.analyzers.RequestCounter;
import backend.academy.loganalyzer.analyzer.analyzers.RequestFrequencyAnalyzer;
import backend.academy.loganalyzer.analyzer.analyzers.ResponseCodeAnalyzer;
import backend.academy.loganalyzer.analyzer.analyzers.ResponseSizeAnalyzer;
import backend.academy.loganalyzer.analyzer.analyzers.UniqueIpAnalyzer;
import backend.academy.loganalyzer.error.calculation.CalculateException;
import backend.academy.loganalyzer.error.calculation.PercentileCalculationException;
import backend.academy.loganalyzer.model.LogAnalysisMetrics;
import backend.academy.loganalyzer.model.LogEntry;
import java.util.List;

/**
 * The LogAnalyzer class is responsible for analyzing log entries and extracting key metrics
 * such as total request count, popular resources, response code frequency, average response size,
 * 95th percentile of response sizes, unique IP addresses count, and request frequency by hour.
 */
public class LogAnalyzer {

    private static final int PRECENT = 95;

    /**
     * Analyzes a list of log entries and computes various metrics.
     *
     * @param logEntries The list of log entries to be analyzed.
     * @return LogAnalysisMetrics containing the computed metrics.
     */
    public LogAnalysisMetrics analyze(List<LogEntry> logEntries) {
        try {
            LogAnalysisMetrics result = new LogAnalysisMetrics();

            // Count total requests
            RequestCounter counter = new RequestCounter();
            result.totalRequests(counter.countTotalRequests(logEntries));

            // Most frequently requested resources
            PopularResourceAnalyzer popularResourceAnalyzer = new PopularResourceAnalyzer();
            result.mostPopularResources(popularResourceAnalyzer.getMostPopularResources(logEntries));

            // Most common response codes
            ResponseCodeAnalyzer responseCodeAnalyzer = new ResponseCodeAnalyzer();
            result.mostCommonResponseCodes(responseCodeAnalyzer.getMostCommonResponseCodes(logEntries));

            // Average server response size
            ResponseSizeAnalyzer sizeAnalyzer = new ResponseSizeAnalyzer();
            result.averageResponseSize(sizeAnalyzer.calculateAverageSize(logEntries));

            // Calculate 95th percentile
            PercentileCalculator percentileCalculator = new PercentileCalculator();
            result.responseSizePercentile95(percentileCalculator.calculatePercentile(logEntries, PRECENT));

            // Count unique IP addresses
            UniqueIpAnalyzer uniqueIpAnalyzer = new UniqueIpAnalyzer();
            result.uniqueIpCount(uniqueIpAnalyzer.countUniqueIps(logEntries));

            // Request frequency by hour
            RequestFrequencyAnalyzer frequencyAnalyzer = new RequestFrequencyAnalyzer();
            result.requestFrequencyByHour(frequencyAnalyzer.countRequestsByHour(logEntries));

            return result;
        } catch (PercentileCalculationException e) {
            throw new CalculateException("An error occurred while analyzing the logs.", e);
        }
    }
}
