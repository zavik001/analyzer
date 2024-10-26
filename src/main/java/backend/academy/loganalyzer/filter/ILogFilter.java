package backend.academy.loganalyzer.filter;

import backend.academy.loganalyzer.error.reading.filter.InvalidFilterException;
import backend.academy.loganalyzer.model.LogEntry;

/**
 * Interface for filtering log entries.
 */
public interface ILogFilter {
    /**
     * Filters a log entry according to the specified parameters.
     *
     * @param logEntry    the log entry to filter
     * @param fromDate    the start date for filtering (may be null)
     * @param toDate      the end date for filtering (may be null)
     * @param filterField the name of the field to filter (may be null)
     * @param filterValue the value of the field to filter (may be null)
     * @return true if the entry matches the filtering criteria, otherwise false
     * @throws InvalidFilterException if there is an error with the filter values
     */
    boolean filter(LogEntry logEntry, String fromDate, String toDate, String filterField, String filterValue)
        throws InvalidFilterException;
}
