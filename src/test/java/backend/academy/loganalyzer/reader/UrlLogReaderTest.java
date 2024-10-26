// package backend.academy.loganalyzer.reader;

// import backend.academy.loganalyzer.error.reading.LogReadingException;
// import backend.academy.loganalyzer.model.LogEntry;
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
// import java.util.List;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.powermock.api.mockito.PowerMockito;
// import org.powermock.core.classloader.annotations.PrepareForTest;
// import static org.assertj.core.api.Assertions.assertThat;
// import static org.assertj.core.api.Assertions.assertThatThrownBy;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.when;

// @ExtendWith(MockitoExtension.class)
// @PrepareForTest({UrlLogReader.class, HttpClient.class})
// class UrlLogReaderTest {

//     @InjectMocks
//     private UrlLogReader urlLogReader;

//     @Mock
//     private HttpClient httpClientMock;

//     @BeforeEach
//     void setUp() {
//         PowerMockito.mockStatic(HttpClient.class);
//         when(HttpClient.newBuilder().build()).thenReturn(httpClientMock);
//     }

//     @Test
//     void shouldReadAndFilterLogsSuccessfully() throws Exception {
//         // Arrange
//         String urlPath = "http://test.com/logs";
//         String logData = "192.168.1.1 - user1 [2024-10-25] \"GET /index.html HTTP/1.1\" 200 1234 \"-\" \"Mozilla\"";
//         HttpResponse<String> responseMock = mock(HttpResponse.class);
        
//         when(responseMock.statusCode()).thenReturn(200);
//         when(responseMock.body()).thenReturn(logData);
//         when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
//                 .thenReturn(responseMock);

//         // Act
//         List<LogEntry> logEntries = urlLogReader.readLogs(urlPath, "2024-10-24", "2024-10-26", "statusCode", "200");

//         // Assert
//         assertThat(logEntries).hasSize(1);
//         assertThat(logEntries.get(0).statusCode()).isEqualTo(200);
//         assertThat(logEntries.get(0).userId()).isEqualTo("user1");
//     }

//     @Test
//     void shouldThrowExceptionForInvalidUrl() {
//         // Arrange
//         String invalidUrl = "htt://invalid-url";

//         // Act & Assert
//         assertThatThrownBy(() -> urlLogReader.readLogs(invalidUrl, "2024-10-24", "2024-10-26", "statusCode", "200"))
//                 .isInstanceOf(LogReadingException.class)
//                 .hasMessageContaining("Error reading logs from URL");
//     }

//     @Test
//     void shouldThrowExceptionForNon200Status() throws Exception {
//         // Arrange
//         String urlPath = "http://test.com/logs";
//         HttpResponse<String> responseMock = mock(HttpResponse.class);
        
//         when(responseMock.statusCode()).thenReturn(404);
//         when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
//                 .thenReturn(responseMock);

//         // Act & Assert
//         assertThatThrownBy(() -> urlLogReader.readLogs(urlPath, "2024-10-24", "2024-10-26", "statusCode", "200"))
//                 .isInstanceOf(LogReadingException.class)
//                 .hasMessageContaining("Failed to fetch logs: HTTP response code 404");
//     }
// }
