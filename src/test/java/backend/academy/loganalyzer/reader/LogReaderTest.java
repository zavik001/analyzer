// package backend.academy.loganalyzer.reader;

// import backend.academy.loganalyzer.error.reading.LogReadingException;
// import backend.academy.loganalyzer.filter.ILogFilter;
// // import backend.academy.loganalyzer.model.LogEntry;
// // import java.io.IOException;
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
// // import java.nio.file.Files;
// // import java.nio.file.Path;
// // import java.util.List;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// // import static org.assertj.core.api.Assertions.assertThat;
// import static org.assertj.core.api.Assertions.assertThatThrownBy;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.doReturn;
// import static org.mockito.Mockito.when;


// public class LogReaderTest {

//     @InjectMocks
//     private FileLogReader fileLogReader;

//     @InjectMocks
//     private UrlLogReader urlLogReader;

//     @Mock
//     private ILogFilter logFilter;

//     @Mock
//     private HttpClient httpClient;

//     @Mock
//     private HttpResponse<String> httpResponse;

//     @BeforeEach
//     public void setup() {
//         MockitoAnnotations.openMocks(this);
//     }

//     // @Test
//     // public void shouldReadAndFilterLogsFromFile() throws IOException, LogReadingException {
//     //     // Arrange
//     //     String sampleLog = "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3\"";
//     //     Path tempFile = Files.createTempFile("sample", ".log");
//     //     Files.write(tempFile, List.of(sampleLog));

//     //     // Mock the filter behavior
//     //     LogEntry mockLogEntry = new LogEntry("93.180.71.3", "-", "-", "17/May/2015:08:05:32 +0000", "GET",
//     //             "/downloads/product_1", "HTTP/1.1", 304, 0, "-", "Debian APT-HTTP/1.3");
//     //     when(logFilter.filter(any(LogEntry.class), any(), any(), any(), any())).thenReturn(true);

//     //     // Act
//     //     List<LogEntry> result = fileLogReader.readLogs(tempFile.toString(), null, null, null, null);

//     //     // Assert
//     //     assertThat(result).hasSize(1);
//     //     assertThat(result.get(0).ipAddress()).isEqualTo("93.180.71.3");

//     //     // Cleanup
//     //     Files.deleteIfExists(tempFile);
//     // }

//     @Test
//     public void shouldThrowExceptionForInvalidFilePath() {
//         // Arrange
//         String invalidPath = "/invalid/path/to/logfile.log";

//         // Act & Assert
//         assertThatThrownBy(() -> fileLogReader.readLogs(invalidPath, null, null, null, null))
//                 .isInstanceOf(LogReadingException.class)
//                 .hasMessageContaining("Error reading file");
//     }

//     // @Test
//     // public void shouldReadAndFilterLogsFromUrl() throws Exception {
//     //     // Arrange
//     //     String url = "http://example.com/logs";
//     //     String sampleLog = "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3\"";
//     //     when(httpResponse.statusCode()).thenReturn(200);
//     //     when(httpResponse.body()).thenReturn(sampleLog);

//     //     // Ensure `httpClient.send` is correctly mocked to return `httpResponse`
//     //     doReturn(httpResponse).when(httpClient).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));

//     //     LogEntry mockLogEntry = new LogEntry("93.180.71.3", "-", "-", "17/May/2015:08:05:32 +0000", "GET",
//     //             "/downloads/product_1", "HTTP/1.1", 304, 0, "-", "Debian APT-HTTP/1.3");
//     //     when(logFilter.filter(any(LogEntry.class), any(), any(), any(), any())).thenReturn(true);

//     //     // Act
//     //     List<LogEntry> result = urlLogReader.readLogs(url, null, null, null, null);

//     //     // Assert
//     //     assertThat(result).hasSize(1);
//     //     assertThat(result.get(0).ipAddress()).isEqualTo("93.180.71.3");
//     // }

//     @Test
//     public void shouldThrowExceptionForInvalidUrlResponse() throws Exception {
//         // Arrange
//         String url = "http://example.com/logs";
//         when(httpResponse.statusCode()).thenReturn(404);

//         // Ensure `httpClient.send` is correctly mocked to return `httpResponse`
//         doReturn(httpResponse).when(httpClient).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));

//         // Act & Assert
//         assertThatThrownBy(() -> urlLogReader.readLogs(url, null, null, null, null))
//                 .isInstanceOf(LogReadingException.class)
//                 .hasMessageContaining("Error reading logs from URL: http://example.com/logs");
//     }

// }
