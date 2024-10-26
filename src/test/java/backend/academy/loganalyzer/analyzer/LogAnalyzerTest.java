package backend.academy.loganalyzer.analyzer;

import backend.academy.loganalyzer.analyzer.analyzers.PercentileCalculator;
import backend.academy.loganalyzer.analyzer.analyzers.RequestCounter;
import backend.academy.loganalyzer.analyzer.analyzers.RequestFrequencyAnalyzer;
import backend.academy.loganalyzer.analyzer.analyzers.ResponseCodeAnalyzer;
import backend.academy.loganalyzer.analyzer.analyzers.ResponseSizeAnalyzer;
import backend.academy.loganalyzer.analyzer.analyzers.UniqueIpAnalyzer;
import backend.academy.loganalyzer.analyzer.analyzers.PopularResourceAnalyzer;
import backend.academy.loganalyzer.error.calculation.CalculateException;
import backend.academy.loganalyzer.model.LogAnalysisMetrics;
import backend.academy.loganalyzer.model.LogEntry;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class LogAnalyzerTest {

    @InjectMocks
    private LogAnalyzer logAnalyzer;

    @Mock
    private RequestCounter requestCounter;

    @Mock
    private PopularResourceAnalyzer popularResourceAnalyzer;

    @Mock
    private ResponseCodeAnalyzer responseCodeAnalyzer;

    @Mock
    private ResponseSizeAnalyzer responseSizeAnalyzer;

    @Mock
    private PercentileCalculator percentileCalculator;

    @Mock
    private UniqueIpAnalyzer uniqueIpAnalyzer;

    @Mock
    private RequestFrequencyAnalyzer requestFrequencyAnalyzer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void analyze_shouldReturnCorrectMetrics_whenLogEntriesAreProvided() {
        // Arrange
        List<LogEntry> logEntries = createSampleLogEntries();
        when(requestCounter.countTotalRequests(logEntries)).thenReturn(6);
        when(popularResourceAnalyzer.getMostPopularResources(logEntries)).thenReturn(List.of("/downloads/product_1", "/downloads/product_2"));
        when(responseCodeAnalyzer.getMostCommonResponseCodes(logEntries)).thenReturn(Map.of(200, 2L, 304, 4L));
        when(responseSizeAnalyzer.calculateAverageSize(logEntries)).thenReturn(163.33333333333334);
        when(percentileCalculator.calculatePercentile(logEntries, 95)).thenReturn(490.0);
        when(uniqueIpAnalyzer.countUniqueIps(logEntries)).thenReturn(3);
        when(requestFrequencyAnalyzer.countRequestsByHour(logEntries)).thenReturn(Map.of("2015-05-17 08", 6L));

        // Act
        LogAnalysisMetrics result = logAnalyzer.analyze(logEntries);

        // Assert
        assertThat(result.totalRequests()).isEqualTo(6);
        assertThat(result.mostPopularResources()).containsExactly("/downloads/product_1", "/downloads/product_2");
        assertThat(result.mostCommonResponseCodes()).containsEntry(200, 2L).containsEntry(304, 4L);
        assertThat(result.averageResponseSize()).isEqualTo(163.33333333333334);
        assertThat(result.responseSizePercentile95()).isEqualTo(490.0);
        assertThat(result.uniqueIpCount()).isEqualTo(3);
        assertThat(result.requestFrequencyByHour()).containsEntry("2015-05-17 08", 6L);
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 101 })
    void analyze_shouldThrowCalculateException_whenInvalidPercentileProvided(int invalidPercentile) {
        // Arrange
        List<LogEntry> logEntries = createSampleLogEntries();
        doThrow(new CalculateException("Invalid percentile")).when(percentileCalculator).calculatePercentile(logEntries,
                invalidPercentile);

        // Act & Assert
        assertThatThrownBy(() -> percentileCalculator.calculatePercentile(logEntries, invalidPercentile))
                .isInstanceOf(CalculateException.class)
                .hasMessageContaining("Invalid percentile");
    }

    private List<LogEntry> createSampleLogEntries() {
        // Manually create sample log entries based on the structure of your LogEntry
        // class
        return List.of(
                new LogEntry("93.180.71.3", "-", "-", "17/May/2015:08:05:32 +0000", "GET", "/downloads/product_1",
                        "HTTP/1.1", 304, 0, "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"),
                new LogEntry("93.180.71.3", "-", "-", "17/May/2015:08:05:23 +0000", "GET", "/downloads/product_1",
                        "HTTP/1.1", 304, 0, "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"),
                new LogEntry("80.91.33.133", "-", "-", "17/May/2015:08:05:24 +0000", "GET", "/downloads/product_1",
                        "HTTP/1.1", 304, 0, "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"),
                new LogEntry("217.168.17.5", "-", "-", "17/May/2015:08:05:34 +0000", "GET", "/downloads/product_1",
                        "HTTP/1.1", 200, 490, "-", "Debian APT-HTTP/1.3 (0.8.10.3)"),
                new LogEntry("217.168.17.5", "-", "-", "17/May/2015:08:05:09 +0000", "GET", "/downloads/product_2",
                        "HTTP/1.1", 200, 490, "-", "Debian APT-HTTP/1.3 (0.8.10.3)"),
                new LogEntry("93.180.71.3", "-", "-", "17/May/2015:08:05:57 +0000", "GET", "/downloads/product_1",
                        "HTTP/1.1", 304, 0, "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"));
    }
}
