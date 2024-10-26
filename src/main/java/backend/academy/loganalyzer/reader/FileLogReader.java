package backend.academy.loganalyzer.reader;

import backend.academy.loganalyzer.error.reading.LogReadingException;
import backend.academy.loganalyzer.model.LogEntry;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

/**
 * A {@link FileLogReader} that reads log entries from a local file.
 * Supports filtering of log entries by date range and field value.
 */
public class FileLogReader extends AbstractLogReader {

    /**
     * Reads log entries from a specified file path and filters them according to the provided criteria.
     * The method performs path sanitization and normalization before accessing the file.
     *
     * @param path         the path to the log file.
     * @param fromDate     the start date for filtering log entries.
     * @param toDate       the end date for filtering log entries.
     * @param filterField  the field name used for filtering.
     * @param filterValue  the expected value of the filter field.
     * @return a list of {@link LogEntry} objects that match the filter criteria.
     * @throws LogReadingException if an I/O error occurs while reading the file.
     */
    @Override
    public List<LogEntry> readLogs(String path, String fromDate, String toDate, String filterField, String filterValue)
            throws LogReadingException {

        List<LogEntry> logEntries = new ArrayList<>();
        try {
            String sanitizedPath = FilenameUtils.getName(path);
            Path filePath = Paths.get(sanitizedPath).normalize().toRealPath();

            try (var lines = Files.lines(filePath)) {
                lines.forEach(line -> {
                    LogEntry logEntry = createLogEntry(line);
                    if (logEntry != null && logFilter.filter(logEntry, fromDate, toDate, filterField, filterValue)) {
                        logEntries.add(logEntry);
                    }
                });
            }
        } catch (IOException e) {
            throw new LogReadingException("Error reading file: " + path, e);
        }
        return logEntries;
    }
}
