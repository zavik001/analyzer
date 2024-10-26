// package backend.academy.loganalyzer.reader;

// import backend.academy.loganalyzer.error.reading.LogReadingException;
// import backend.academy.loganalyzer.model.LogEntry;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.util.List;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.assertj.core.api.Assertions.assertThat;
// import static org.assertj.core.api.Assertions.assertThatThrownBy;

// class FileLogReaderTest {

//     private FileLogReader fileLogReader;

//     @BeforeEach
//     void setUp() {
//         fileLogReader = new FileLogReader();
//     }

//     @Test
//     void shouldReadAndFilterLogsSuccessfully() throws Exception {
//         // Arrange
//         Path tempFile = Files.createTempFile("test-log", ".log");
//         try {
//             Files.writeString(tempFile, "192.168.1.1 - user1 [17/May/2015:08:05:32 +0000]"
//                 + " \"GET /index.html HTTP/1.1\" 200 1234 \"-\" \"Mozilla\"");

//             // Act
//             List<LogEntry> logEntries = fileLogReader.readLogs(tempFile.toAbsolutePath().toString(),
//                 "2010-10-24", "2024-10-26", "statusCode", "200");

//             // Assert
//             assertThat(logEntries).hasSize(1);
//             assertThat(logEntries.get(0).statusCode()).isEqualTo(200);
//             assertThat(logEntries.get(0).userId()).isEqualTo("user1");

//         } finally {
//             Files.deleteIfExists(tempFile);
//         }
//     }

//     @Test
//     void shouldThrowExceptionForNonExistentFile() {
//         // Arrange
//         String nonExistentPath = "non-existent-file.log";

//         // Act & Assert
//         assertThatThrownBy(() -> fileLogReader.readLogs(
//                 nonExistentPath, "2024-10-24", "2024-10-26", "statusCode", "200"))
//                 .isInstanceOf(LogReadingException.class)
//                 .hasMessageContaining("Error reading file");
//     }
// }
