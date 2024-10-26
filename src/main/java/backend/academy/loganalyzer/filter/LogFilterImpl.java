package backend.academy.loganalyzer.filter;

import backend.academy.loganalyzer.error.reading.filter.InvalidFilterException;
import backend.academy.loganalyzer.filter.field.FieldLogFilter;
import backend.academy.loganalyzer.filter.time.TimeLogFilter;
import backend.academy.loganalyzer.model.LogEntry;
import lombok.NoArgsConstructor;

/**
 * Composite class for filtering log entries by time and fields.
 */
@NoArgsConstructor
public class LogFilterImpl implements ILogFilter {

    private final TimeLogFilter timeLogFilter = new TimeLogFilter();
    private final FieldLogFilter fieldLogFilter = new FieldLogFilter();

    @Override
    public boolean filter(LogEntry logEntry, String fromDate, String toDate, String filterField, String filterValue)
            throws InvalidFilterException {

        boolean isTimeFilterPresent = fromDate != null || toDate != null;
        boolean isFieldFilterPresent = filterField != null && filterValue != null;

        boolean result = true;

        if (isTimeFilterPresent) {
            result = timeLogFilter.filterByTime(logEntry.timestamp(), fromDate, toDate);
            if (result && isFieldFilterPresent) {
                result = fieldLogFilter.filterByField(logEntry, filterField, filterValue);
            }
        } else if (isFieldFilterPresent) {
            result = fieldLogFilter.filterByField(logEntry, filterField, filterValue);
        }

        return result;
    }
}
