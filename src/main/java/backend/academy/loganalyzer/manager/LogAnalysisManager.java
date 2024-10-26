package backend.academy.loganalyzer.manager;

import backend.academy.loganalyzer.analyzer.LogAnalyzer;
import backend.academy.loganalyzer.error.calculation.CalculateException;
import backend.academy.loganalyzer.error.parsing.LogParsingException;
import backend.academy.loganalyzer.error.reading.LogReadingException;
import backend.academy.loganalyzer.error.writer.LogWritingException;
import backend.academy.loganalyzer.model.LogAnalysisMetrics;
import backend.academy.loganalyzer.model.LogEntry;
import backend.academy.loganalyzer.model.ParsedArguments;
import backend.academy.loganalyzer.parser.ArgumentParser;
import backend.academy.loganalyzer.reader.ILogReader;
import backend.academy.loganalyzer.reader.factory.LogReaderFactory;
import backend.academy.loganalyzer.report.IReportWriter;
import backend.academy.loganalyzer.report.factory.ReportWriterFactory;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Main manager class responsible for running the log analysis process.
 * This includes parsing arguments, reading logs, analyzing metrics,
 * and generating a formatted report.
 */
@Slf4j
@AllArgsConstructor
public class LogAnalysisManager {

    private final String[] args;
    private final String reportFilePath;

    /**
     * Executes the log analysis process. This includes:
     * parsing input arguments, determining the report format, reading log files,
     * analyzing log data, and writing the report output.
     */
    public void run() {
        try {

            // Parse arguments from the command line
            log.info("Starting argument parsing");
            ArgumentParser argumentParser = new ArgumentParser();
            ParsedArguments parsedArgs = argumentParser.parse(args);
            log.info("Arguments parsed successfully: {}", parsedArgs);


            // Determine the report format and create the report writer
            log.info("Creating report file with specified format");
            if (parsedArgs.format() == null) {
                log.info("Report format not specified. Using default format 'markdown'");
                parsedArgs.format("markdown");
            }
            IReportWriter reportWriter = ReportWriterFactory.getReportWriter(parsedArgs.format(),
                    reportFilePath + parsedArgs.format());
            log.info("Report file created successfully {}",
                reportFilePath + parsedArgs.format());


            // Initialize the analyzer for log processing
            LogAnalyzer analyzer = new LogAnalyzer();


            // Process each provided log path
            for (String path : parsedArgs.paths()) {
                log.info("Starting processing for path: {}", path);

                // Get the appropriate log reader (URL or file-based)
                ILogReader logReader = LogReaderFactory.getLogReader(path);
                List<LogEntry> logEntries = logReader.readLogs(path,
                    parsedArgs.fromDate(), parsedArgs.toDate(), parsedArgs.filterField(), parsedArgs.filterValue());

                log.info("Logs read successfully from path: {}", path);

                // Analyze the retrieved log data
                LogAnalysisMetrics metrics = analyzer.analyze(logEntries);

                // Write analysis results to the report
                reportWriter.writeHeader("Analysis - " + path);
                reportWriter.writeMetric("Total request count", metrics.totalRequests());
                reportWriter.writeMetric("Most popular resources", metrics.mostPopularResources().toString());
                reportWriter.writeMetric("Most common response codes", metrics.mostCommonResponseCodes().toString());
                reportWriter.writeMetric("Average response size", metrics.averageResponseSize());
                reportWriter.writeMetric("95th percentile of response size", metrics.responseSizePercentile95());
                reportWriter.writeMetric("Unique IP count", metrics.uniqueIpCount());
                reportWriter.writeMetric("Hourly request frequency", metrics.requestFrequencyByHour().toString());
            }


            // Finalize the report
            reportWriter.writeFooter();
            reportWriter.close();

        } catch (LogParsingException e) {
            // Handle errors encountered during argument parsing
            log.error("Argument parsing error: {}", e.getMessage());
            log.info("Usage example: --path <log-path> [--from <start-date: yyyy-MM-dd>] [--to <end-date: yyyy-MM-dd>]"
                + " [--filter-field <field> --filter-value <value>] [--format <output-format>]");
        } catch (LogWritingException e) {
            // Handle errors encountered during report generation
            log.error("Error creating report writer", e);
        } catch (CalculateException e) {
            // Handle errors encountered during log metric calculation
            log.error("Error calculating metric", e);
        } catch (LogReadingException e) {
            // Handle errors encountered during log reading
            log.error("Log reading error: {}", e.getMessage());
        } catch (Exception e) {
            // Handle any other unexpected errors
            log.error("An unexpected error occurred", e);
        }
    }
}
