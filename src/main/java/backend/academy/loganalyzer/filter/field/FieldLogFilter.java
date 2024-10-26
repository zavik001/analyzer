package backend.academy.loganalyzer.filter.field;

import backend.academy.loganalyzer.error.reading.filter.InvalidFilterException;
import backend.academy.loganalyzer.model.LogEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Class for filtering log entries by a specified field and value.
 */
public class FieldLogFilter {
    private final Map<String, Function<LogEntry, String>> fieldExtractors = new HashMap<>();

    public FieldLogFilter() {
        fieldExtractors.put("ipAddress", LogEntry::ipAddress);
        fieldExtractors.put("userIdentifier", LogEntry::userIdentifier);
        fieldExtractors.put("userId", LogEntry::userId);
        fieldExtractors.put("timestamp", LogEntry::timestamp);
        fieldExtractors.put("requestMethod", LogEntry::requestMethod);
        fieldExtractors.put("requestPath", LogEntry::requestPath);
        fieldExtractors.put("protocol", LogEntry::protocol);
        fieldExtractors.put("statusCode", entry -> String.valueOf(entry.statusCode()));
        fieldExtractors.put("responseSize", entry -> String.valueOf(entry.responseSize()));
        fieldExtractors.put("referrer", LogEntry::referrer);
        fieldExtractors.put("userAgent", LogEntry::userAgent);
    }

    /**
     * Checks if the value of the specified field in the log entry matches the filter value.
     *
     * @param entry         the log entry
     * @param field         the field to filter by
     * @param expectedValue the expected value for filtering
     * @return true if the field value matches the filter value, otherwise false
     * @throws InvalidFilterException if the field is invalid
     */
    public boolean filterByField(LogEntry entry, String field, String expectedValue) throws InvalidFilterException {
        String actualValue = getFieldValue(entry, field);

        if (actualValue == null) {
            throw new InvalidFilterException("Invalid field for filtering: " + field);
        }

        return actualValue.equals(expectedValue);
    }

    private String getFieldValue(LogEntry entry, String field) {
        Function<LogEntry, String> extractor = fieldExtractors.get(field);
        return extractor != null ? extractor.apply(entry) : null;
    }
}
