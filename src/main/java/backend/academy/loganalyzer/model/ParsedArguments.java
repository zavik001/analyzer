package backend.academy.loganalyzer.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Model representing parsed command-line arguments for log analysis.
 * Holds paths to logs, analysis format, date range, and filtering criteria.
 */
@Getter
@Setter
@ToString
public class ParsedArguments {

    /**
     * List of file paths or URLs for logs to be analyzed.
     */
    private List<String> paths;

    /**
     * Format of the report output, such as "markdown" or "asciidoc".
     */
    private String format;

    /**
     * Start date for log analysis in a specified format (e.g., "YYYY-MM-DD").
     */
    private String fromDate;

    /**
     * End date for log analysis in a specified format (e.g., "YYYY-MM-DD").
     */
    private String toDate;

    /**
     * Field in log entries to filter by, e.g., "status_code" or "ip".
     */
    private String filterField;

    /**
     * Value used to filter log entries based on the specified filter field.
     */
    private String filterValue;
}
