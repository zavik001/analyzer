package backend.academy.loganalyzer.reader;

import backend.academy.loganalyzer.error.reading.LogReadingException;
import backend.academy.loganalyzer.model.LogEntry;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A {@link UrlLogReader} that reads log entries from a specified URL.
 * Uses HTTP GET to retrieve log data and filters it based on specified criteria.
 */
public class UrlLogReader extends AbstractLogReader {

    private static final int CONNECTION_TIMEOUT_MS = 5000;
    private static final int STATUS = 200;

    /**
     * Reads log entries from a specified URL and filters them according to the provided criteria.
     *
     * @param urlPath      the URL from which log data is retrieved.
     * @param fromDate     the start date for filtering log entries.
     * @param toDate       the end date for filtering log entries.
     * @param filterField  the field name used for filtering.
     * @param filterValue  the expected value of the filter field.
     * @return a list of {@link LogEntry} objects that match the filter criteria.
     * @throws LogReadingException if an error occurs while reading data from the URL or
     * if the response status is invalid.
     */
    @Override
    public List<LogEntry> readLogs(
            String urlPath, String fromDate, String toDate, String filterField, String filterValue)
            throws LogReadingException {

        try {
            URI uri = new URI(urlPath);
            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.NEVER)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .timeout(Duration.ofMillis(CONNECTION_TIMEOUT_MS))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != STATUS) {
                throw new LogReadingException("Failed to fetch logs: HTTP response code " + response.statusCode());
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new java.io.ByteArrayInputStream(response.body().getBytes(StandardCharsets.UTF_8)),
                            StandardCharsets.UTF_8))) {

                return reader.lines()
                        .map(this::createLogEntry)
                        .filter(logEntry -> logEntry != null
                                && logFilter.filter(logEntry, fromDate, toDate, filterField, filterValue))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            throw new LogReadingException("Error reading logs from URL: " + urlPath, e);
        }
    }
}
