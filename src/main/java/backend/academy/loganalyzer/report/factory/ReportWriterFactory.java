package backend.academy.loganalyzer.report.factory;

import backend.academy.loganalyzer.error.writer.InvalidFormatException;
import backend.academy.loganalyzer.error.writer.LogWritingException;
import backend.academy.loganalyzer.report.AsciidocReportWriter;
import backend.academy.loganalyzer.report.IReportWriter;
import backend.academy.loganalyzer.report.MarkdownReportWriter;
import lombok.experimental.UtilityClass;

/**
 * Factory for creating instances of {@link IReportWriter} based on the specified format.
 */
@UtilityClass
public class ReportWriterFactory {

    /**
     * Creates a {@link IReportWriter} based on the specified report format.
     *
     * @param format  the report format ("adoc" for Asciidoc, "markdown" for Markdown)
     * @param filePath the path to the file where the report will be written
     * @return {@link IReportWriter} for the specified format
     * @throws LogWritingException if an error occurs while creating the instance
     */
    public static IReportWriter getReportWriter(String format, String filePath) {
        try {
            if (format == null) {
                return new MarkdownReportWriter(filePath);
            }

            switch (format.toLowerCase()) {
                case "adoc":
                case "asciidoc":
                    return new AsciidocReportWriter(filePath);
                case "markdown":
                    return new MarkdownReportWriter(filePath);
                default:
                    throw new InvalidFormatException("Unsupported format: " + format);
            }
        } catch (InvalidFormatException e) {
            throw new LogWritingException("Error creating ReportWriter for format: " + format, e);
        }
    }
}
