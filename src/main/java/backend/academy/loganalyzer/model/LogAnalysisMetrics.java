package backend.academy.loganalyzer.model;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * A class that represents the metrics of a log analysis.
 * <p>
 * An instance of this class contains the results of a log analysis, such as the total number of requests,
 * the most popular resources, the most common response codes, the average and 95th percentile of response
 * sizes, the number of unique IP addresses, and the frequency of requests by hour.
 * </p>
 */
@Getter
@Setter
public class LogAnalysisMetrics {

    /**
     * The total number of requests processed during the log analysis.
     */
    private int totalRequests;

    /**
     * A list of the most popular resources accessed in the logs.
     */
    private List<String> mostPopularResources;

    /**
     * A map that stores the most common response codes and their counts.
     * The key is the HTTP status code, and the value is the number of times it appeared in the logs.
     */
    private Map<Integer, Long> mostCommonResponseCodes;

    /**
     * The average size of responses returned during the log analysis, measured in bytes.
     */
    private double averageResponseSize;

    /**
     * The 95th percentile of response sizes, indicating that 95% of responses are smaller than this value.
     */
    private double responseSizePercentile95;

    /**
     * The count of unique IP addresses that accessed the resources during the log analysis.
     */
    private int uniqueIpCount;

    /**
     * A map that tracks the frequency of requests by hour.
     * The key is the hour (in a suitable format), and the value is the count of requests made during that hour.
     */
    private Map<String, Long> requestFrequencyByHour;
}
