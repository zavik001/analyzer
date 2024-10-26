package backend.academy.loganalyzer.report;

import backend.academy.loganalyzer.error.writer.LogWritingException;
import java.io.IOException;

/**
 * Implementation of {@link IReportWriter} for generating reports in Asciidoc format.
 */
public class AsciidocReportWriter extends BaseReportWriter {

    public AsciidocReportWriter(String filePath) {
        super(filePath);
    }

    @Override
    public void writeHeader(String fileName) {
        try {
            initializeWriter();
            writer.write("= Log Analysis Report: " + fileName + DOUBLE_NEWLINE);
        } catch (IOException | LogWritingException e) {
            throw new LogWritingException("Error writing header to report: " + fileName + e.getMessage(), e);
        }
    }

    @Override
    public void writeMetric(String metricName, Object value) {
        try {
            initializeWriter();
            writer.write("== " + metricName + "\n");
            writer.write(value.toString() + DOUBLE_NEWLINE);
        } catch (IOException | LogWritingException e) {
            throw new LogWritingException("Error writing metric: " + metricName, e);
        }
    }

    @Override
    public void writeFooter() {
        try {
            initializeWriter();
            writer.write("\n----\n\n");
            writer.write("Report generated automatically.");
        } catch (IOException | LogWritingException e) {
            throw new LogWritingException("Error writing report footer", e);
        }
    }
}
