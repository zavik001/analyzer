package backend.academy.loganalyzer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a single log entry with various request details, such as IP address, timestamp,
 * request method, request path, status code, and user agent.
 *
 * <p>Example of a log entry:
 * <pre>
 *     93.180.71.3 - - [17/May/2015:08:05:32 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-"
 *     "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
 * </pre>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogEntry {

    /**
     * The IP address from which the request originated.
     */
    private String ipAddress;

    /**
     * The user identifier, or '-' if not provided in the log.
     */
    private String userIdentifier;

    /**
     * The user login ID, or '-' if not provided in the log.
     */
    private String userId;

    /**
     * The timestamp of the request in the log entry.
     */
    private String timestamp;

    /**
     * The HTTP method used for the request (e.g., GET, POST).
     */
    private String requestMethod;

    /**
     * The requested URL path.
     */
    private String requestPath;

    /**
     * The HTTP protocol version used for the request (e.g., HTTP/1.1).
     */
    private String protocol;

    /**
     * The HTTP status code returned in response to the request (e.g., 200, 404).
     */
    private int statusCode;

    /**
     * The size of the response in bytes.
     */
    private int responseSize;

    /**
     * The referrer URL, indicating the source of the request.
     */
    private String referrer;

    /**
     * The user-agent string, providing client information such as browser and OS.
     */
    private String userAgent;
}
