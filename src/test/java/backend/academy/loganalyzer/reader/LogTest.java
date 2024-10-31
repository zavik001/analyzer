package backend.academy.loganalyzer.reader;

import backend.academy.loganalyzer.error.reading.LogReadingException;
import backend.academy.loganalyzer.filter.time.TimeLogFilter;
import backend.academy.loganalyzer.model.LogEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogTest {

    private FileLogReader fileLogReader;
    private UrlLogReader urlLogReader;
    private TimeLogFilter timeLogFilter;

    private static final String VALID_LOG_LINE = "192.168.0.1 - user123 [21/Oct/2023:13:55:36] \"GET /home HTTP/1.1\" 200 512 \"-\" \"Mozilla/5.0\"";
    private static final String INVALID_LOG_LINE = "invalid log line";
    private static final List<String> SAMPLE_LINES = Arrays.asList(VALID_LOG_LINE, INVALID_LOG_LINE);
    private static final String LOG_URL = "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";

    @BeforeEach
    public void setUp() {
        fileLogReader = new FileLogReader();
        urlLogReader = new UrlLogReader();
        timeLogFilter = new TimeLogFilter();
    }

    @Test
    public void testCreateLogEntry_validLine() {
        LogEntry entry = fileLogReader.createLogEntry(VALID_LOG_LINE);
        assertNotNull(entry);
        assertEquals("192.168.0.1", entry.ipAddress());
        assertEquals("user123", entry.userId());
        assertEquals("GET", entry.requestMethod());
        assertEquals("/home", entry.requestPath());
        assertEquals("HTTP/1.1", entry.protocol());
        assertEquals(200, entry.statusCode());
        assertEquals(512, entry.responseSize());
        assertEquals("-", entry.referrer());
        assertEquals("Mozilla/5.0", entry.userAgent());
    }

    @Test
    public void testCreateLogEntry_invalidLine() {
        LogEntry entry = fileLogReader.createLogEntry(INVALID_LOG_LINE);
        assertNull(entry);
    }

    @Test
    public void testFilterLogEntries_byStatusCode() throws LogReadingException {
        List<LogEntry> entries = fileLogReader.filterLogEntries(SAMPLE_LINES, null, null, "statusCode", "200");
        assertEquals(1, entries.size());
        assertEquals(200, entries.get(0).statusCode());
    }

    @Test
    public void testFilterLogEntries_byUserId() throws LogReadingException {
        List<LogEntry> entries = fileLogReader.filterLogEntries(SAMPLE_LINES, null, null, "userId", "user123");
        assertEquals(1, entries.size());
        assertEquals("user123", entries.get(0).userId());
    }

    @Test
    public void testReadLogsFromUrl() throws IOException {
        List<LogEntry> entries = urlLogReader.readLogs(LOG_URL, null, null, null, null);
        assertNotNull(entries);
        assertTrue(entries.size() > 0, "No entries found from URL.");

        LogEntry firstEntry = entries.get(0);
        assertNotNull(firstEntry);
        assertNotNull(firstEntry.ipAddress());
        assertNotNull(firstEntry.requestMethod());
    }

    @Test
    public void testFilterLogEntriesFromUrl_byStatusCode() throws IOException, LogReadingException {
        List<LogEntry> entries = urlLogReader.readLogs(LOG_URL, null, null, "statusCode", "200");
        assertNotNull(entries);

        for (LogEntry entry : entries) {
            assertEquals(200, entry.statusCode());
        }
    }

    @Test
    public void testFilterLogEntriesFromUrl_byUserId() throws IOException, LogReadingException {
        List<LogEntry> entries = urlLogReader.readLogs(LOG_URL, null, null, "ipAddress", "93.180.71.3");
        assertNotNull(entries);

        for (LogEntry entry : entries) {
            assertEquals("93.180.71.3", entry.ipAddress());
        }
    }

    @Test
    public void testFilterLogEntriesFromUrl_byDateRange_From() throws IOException, LogReadingException {
        String fromDate = "2015-05-17"; // yyyy-mm-dd format
        List<LogEntry> entries = urlLogReader.readLogs(LOG_URL, fromDate, null, null, null);
        assertNotNull(entries);

        for (LogEntry entry : entries) {
            String entryDate = entry.timestamp();
            assertTrue(timeLogFilter.filterByTime(entryDate, fromDate, null),
                    "Entry date is before the specified 'from' date: " + entryDate);
        }
    }

    @Test
    public void testFilterLogEntriesFromUrl_byDateRange_To() throws IOException, LogReadingException {
        String toDate = "2015-05-18"; // yyyy-mm-dd format
        List<LogEntry> entries = urlLogReader.readLogs(LOG_URL, null, toDate, null, null);
        assertNotNull(entries);

        for (LogEntry entry : entries) {
            String entryDate = entry.timestamp();
            assertTrue(timeLogFilter.filterByTime(entryDate, null, toDate),
                    "Entry date is after the specified 'to' date: " + entryDate);
        }
    }

    @Test
    public void testFilterLogEntriesFromUrl_byDateRange_FromAndTo() throws IOException, LogReadingException {
        String fromDate = "2015-05-17"; // yyyy-mm-dd format
        String toDate = "2015-05-18"; // yyyy-mm-dd format
        List<LogEntry> entries = urlLogReader.readLogs(LOG_URL, fromDate, toDate, null, null);
        assertNotNull(entries);

        for (LogEntry entry : entries) {
            String entryDate = entry.timestamp();
            assertTrue(timeLogFilter.filterByTime(entryDate, fromDate, toDate),
                    "Entry date is not in the specified range: " + entryDate);
        }
    }

    @Test
    public void testFilterLogEntriesFromUrl_combinedFilters() throws IOException, LogReadingException {
        String fromDate = "2015-05-17"; // yyyy-mm-dd format
        String toDate = "2015-05-18"; // yyyy-mm-dd format
        String userId = "-";
        String statusCode = "200";

        List<LogEntry> entries = urlLogReader.readLogs(LOG_URL, fromDate, toDate, "statusCode", statusCode);
        assertNotNull(entries);

        for (LogEntry entry : entries) {
            assertEquals(200, entry.statusCode());
            assertTrue(timeLogFilter.filterByTime(entry.timestamp(), fromDate, toDate),
                    "Entry date is not in the specified range: " + entry.timestamp());
            assertEquals(userId, entry.userId(), "User ID does not match: " + entry.userId());
        }
    }

    @Test
    public void testFilterLogEntriesFromUrl_combinedFilters_withUserId() throws IOException, LogReadingException {
        String fromDate = "2015-05-17"; // yyyy-mm-dd format
        String toDate = "2015-05-18"; // yyyy-mm-dd format
        String userId = "-";

        List<LogEntry> entries = urlLogReader.readLogs(LOG_URL, fromDate, toDate, null, null);
        assertNotNull(entries);

        for (LogEntry entry : entries) {
            assertTrue(timeLogFilter.filterByTime(entry.timestamp(), fromDate, toDate),
                    "Entry date is not in the specified range: " + entry.timestamp());
            assertEquals(userId, entry.userId(), "User ID does not match: " + entry.userId());
        }
    }

    @Test
    public void testFilterLogEntriesFromUrl_combinedFilters_withStatusCode() throws IOException, LogReadingException {
        String fromDate = "2015-05-17"; // yyyy-mm-dd format
        String toDate = "2015-05-18"; // yyyy-mm-dd format
        String statusCode = "200";

        List<LogEntry> entries = urlLogReader.readLogs(LOG_URL, fromDate, toDate, "statusCode", statusCode);
        assertNotNull(entries);

        for (LogEntry entry : entries) {
            assertTrue(timeLogFilter.filterByTime(entry.timestamp(), fromDate, toDate),
                    "Entry date is not in the specified range: " + entry.timestamp());
            assertEquals(200, entry.statusCode(), "Status code does not match: " + entry.statusCode());
        }
    }
}
