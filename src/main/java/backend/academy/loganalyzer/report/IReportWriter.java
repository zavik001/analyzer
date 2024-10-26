package backend.academy.loganalyzer.report;

/**
 * Interface for report writing with core methods.
 */
public interface IReportWriter {

    /**
     * Writes the report header.
     * @param fileName the name of the report file
     */
    void writeHeader(String fileName);

    /**
     * Writes a metric to the report.
     * @param metricName the name of the metric
     * @param value the value of the metric
     */
    void writeMetric(String metricName, Object value);

    /**
     * Writes the report footer.
     */
    void writeFooter();

    /**
     * Closes the report writer.
     */
    void close();
}
