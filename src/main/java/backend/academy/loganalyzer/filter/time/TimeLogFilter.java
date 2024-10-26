package backend.academy.loganalyzer.filter.time;

import backend.academy.loganalyzer.error.reading.date.InvalidDateException;
import backend.academy.loganalyzer.error.reading.filter.InvalidFilterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Class for filtering log entries based on time parameters.
 */
public class TimeLogFilter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z");

    /**
     * Checks if the log entry matches the specified time range.
     *
     * @param logTimestamp the timestamp of the log entry
     * @param fromDate     the start date for filtering (may be null)
     * @param toDate       the end date for filtering (may be null)
     * @return true if the entry matches the time parameters, otherwise false
     * @throws InvalidFilterException if the date value is invalid
     */
    public boolean filterByTime(String logTimestamp, String fromDate, String toDate) throws InvalidFilterException {
        LocalDateTime logTime = parseLogTimestamp(logTimestamp);
        LocalDateTime from = parseFilterDate(fromDate);
        LocalDateTime to = parseFilterDate(toDate);

        if (from != null && logTime.isBefore(from)) {
            return false;
        }
        if (to != null && logTime.isAfter(to)) {
            return false;
        }

        return true;
    }

    private LocalDateTime parseLogTimestamp(String timestamp) {
        try {
            return LocalDateTime.parse(timestamp.replace("[", "").replace("]", ""), DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Invalid date in logs: " + timestamp, e);
        }
    }

    private LocalDateTime parseFilterDate(String date) {
        if (date == null) {
            return null;
        }
        try {
            return LocalDateTime.parse(date + "T00:00:00");
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Invalid date for filtering: " + date, e);
        }
    }
}
