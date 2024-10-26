package backend.academy.loganalyzer.reader;

import backend.academy.loganalyzer.error.reading.LogReadingException;
import backend.academy.loganalyzer.model.LogEntry;
import java.util.List;

/**
 * Interface for reading and processing log entries from various sources.
 * Provides a method for reading log entries within specified date ranges and
 * filtering them based on given criteria.
 */
public interface ILogReader {

    /**
     * Reads log entries from a specified source and applies optional filtering based on date range and field criteria.
     *
     * @param path the path or URL to the log source.
     * @param fromDate the start date for filtering logs (inclusive), in the expected date format.
     * @param toDate the end date for filtering logs (inclusive), in the expected date format.
     * @param filterField the field name used for filtering log entries, e.g., "statusCode", "userId".
     * @param filterValue the value of the filter field to match log entries against.
     * @return a list of filtered {@link LogEntry} objects that meet the specified criteria.
     * @throws LogReadingException if an error occurs while reading the log source.
     */
    List<LogEntry> readLogs(String path, String fromDate, String toDate, String filterField, String filterValue)
        throws LogReadingException;
}
