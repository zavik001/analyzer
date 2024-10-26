package backend.academy.loganalyzer.reader;

import backend.academy.loganalyzer.error.reading.LogReadingException;
import backend.academy.loganalyzer.filter.ILogFilter;
import backend.academy.loganalyzer.filter.LogFilterImpl;
import backend.academy.loganalyzer.model.LogEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract base class for reading and processing log entries from various sources.
 * Provides common functionality for parsing log entries and filtering them.
 */
public abstract class AbstractLogReader implements ILogReader {

    /**
     * Regular expression pattern for matching and parsing log entries.
     */
    protected static final Pattern LOG_PATTERN = Pattern.compile(
            "^(\\S+) (\\S+) (\\S+) \\[(.*?)\\] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\d+) \"(.*?)\" \"(.*?)\"$");

    // Constants representing capturing groups in the log pattern
    protected static final int GROUP_IP = 1;
    protected static final int GROUP_USER_IDENTIFIER = 2;
    protected static final int GROUP_USER_ID = 3;
    protected static final int GROUP_TIMESTAMP = 4;
    protected static final int GROUP_REQUEST_METHOD = 5;
    protected static final int GROUP_REQUEST_PATH = 6;
    protected static final int GROUP_PROTOCOL = 7;
    protected static final int GROUP_STATUS_CODE = 8;
    protected static final int GROUP_RESPONSE_SIZE = 9;
    protected static final int GROUP_REFERRER = 10;
    protected static final int GROUP_USER_AGENT = 11;

    /**
     * Filter used to apply custom filtering logic to log entries.
     */
    protected final ILogFilter logFilter = new LogFilterImpl();

    /**
     * Parses a single log line into a {@link LogEntry} object.
     *
     * @param line the raw log line as a string.
     * @return a {@link LogEntry} object if parsing is successful; {@code null}
     * if the line does not match the log format.
     */
    protected LogEntry createLogEntry(String line) {
        Matcher matcher = LOG_PATTERN.matcher(line);
        if (!matcher.matches()) {
            return null;  // Early return if line does not match the pattern
        }
        return buildLogEntry(matcher);
    }

    /**
     * Builds a {@link LogEntry} object from a matched log line.
     *
     * @param matcher the {@link Matcher} with matched groups from the log pattern.
     * @return a {@link LogEntry} object created from the matched groups.
     */
    private LogEntry buildLogEntry(Matcher matcher) {
        return new LogEntry(
            matcher.group(GROUP_IP),
            matcher.group(GROUP_USER_IDENTIFIER),
            matcher.group(GROUP_USER_ID),
            matcher.group(GROUP_TIMESTAMP),
            matcher.group(GROUP_REQUEST_METHOD),
            matcher.group(GROUP_REQUEST_PATH),
            matcher.group(GROUP_PROTOCOL),
            Integer.parseInt(matcher.group(GROUP_STATUS_CODE)),
            Integer.parseInt(matcher.group(GROUP_RESPONSE_SIZE)),
            matcher.group(GROUP_REFERRER),
            matcher.group(GROUP_USER_AGENT)
        );
    }

    /**
     * Filters a list of log lines based on specified criteria.
     *
     * @param lines        a list of log lines to be parsed and filtered.
     * @param fromDate     the start date for filtering logs (inclusive).
     * @param toDate       the end date for filtering logs (inclusive).
     * @param filterField  the field name for filtering, such as "statusCode" or "userId".
     * @param filterValue  the value of the filter field to match log entries against.
     * @return a list of {@link LogEntry} objects that match the specified criteria.
     * @throws LogReadingException if an error occurs while filtering log entries.
     */
    protected List<LogEntry> filterLogEntries(List<String> lines, String fromDate,
                                              String toDate, String filterField, String filterValue)
            throws LogReadingException {
        List<LogEntry> logEntries = new ArrayList<>();
        for (String line : lines) {
            LogEntry logEntry = createLogEntry(line);
            if (logEntry != null && logFilter.filter(logEntry, fromDate, toDate, filterField, filterValue)) {
                logEntries.add(logEntry);
            }
        }
        return logEntries;
    }

    /**
     * Reads log entries from a specified source and applies optional filtering based on date range and field criteria.
     *
     * @param path         the path or URL to the log source.
     * @param fromDate     the start date for filtering logs (inclusive).
     * @param toDate       the end date for filtering logs (inclusive).
     * @param filterField  the field name for filtering, such as "statusCode" or "userId".
     * @param filterValue  the value of the filter field to match log entries against.
     * @return a list of {@link LogEntry} objects that meet the specified criteria.
     * @throws LogReadingException if an error occurs while reading or processing the log source.
     */
    public abstract List<LogEntry> readLogs(
            String path, String fromDate, String toDate, String filterField, String filterValue)
            throws LogReadingException;
}
