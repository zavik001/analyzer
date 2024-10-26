package backend.academy.loganalyzer.report;

import backend.academy.loganalyzer.error.writer.LogWritingException;
import java.io.BufferedWriter;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyString;

class MarkdownReportWriterTest {

    @Mock
    private BufferedWriter mockWriter;

    @InjectMocks
    private MarkdownReportWriter markdownReportWriter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        markdownReportWriter = spy(new MarkdownReportWriter("dummyPath"));
        markdownReportWriter.writer = mockWriter;  // Mock BufferedWriter instance
    }

    @Test
    @DisplayName("Should write header in Markdown format")
    void shouldWriteHeader() throws IOException {
        // Act
        markdownReportWriter.writeHeader("sampleFile");

        // Assert
        verify(mockWriter).write("# Log Analysis Report: sampleFile\n\n");
    }

    @Test
    @DisplayName("Should write metric in Markdown format")
    void shouldWriteMetric() throws IOException {
        // Act
        markdownReportWriter.writeMetric("Total Requests", 100);

        // Assert
        verify(mockWriter).write("## Total Requests\n");
        verify(mockWriter).write("100\n\n");
    }

    @Test
    @DisplayName("Should write footer in Markdown format")
    void shouldWriteFooter() throws IOException {
        // Act
        markdownReportWriter.writeFooter();

        // Assert
        verify(mockWriter).write("\n---\n\n");
        verify(mockWriter).write("Report generated automatically.");
    }

    @Test
    @DisplayName("Should throw LogWritingException when IOException occurs in header")
    void shouldThrowExceptionWhenWriteHeaderFails() throws IOException {
        // Arrange
        doThrow(new IOException("IO Error")).when(mockWriter).write(anyString());

        // Act & Assert
        assertThatThrownBy(() -> markdownReportWriter.writeHeader("sampleFile"))
            .isInstanceOf(LogWritingException.class)
            .hasMessageContaining("Error writing header to report");
    }

    @Test
    @DisplayName("Should throw LogWritingException when IOException occurs in metric")
    void shouldThrowExceptionWhenWriteMetricFails() throws IOException {
        // Arrange
        doThrow(new IOException("IO Error")).when(mockWriter).write(anyString());

        // Act & Assert
        assertThatThrownBy(() -> markdownReportWriter.writeMetric("Total Requests", 100))
            .isInstanceOf(LogWritingException.class)
            .hasMessageContaining("Error writing metric");
    }

    @Test
    @DisplayName("Should throw LogWritingException when IOException occurs in footer")
    void shouldThrowExceptionWhenWriteFooterFails() throws IOException {
        // Arrange
        doThrow(new IOException("IO Error")).when(mockWriter).write(anyString());

        // Act & Assert
        assertThatThrownBy(() -> markdownReportWriter.writeFooter())
            .isInstanceOf(LogWritingException.class)
            .hasMessageContaining("Error writing footer");
    }

    @Test
    @DisplayName("Should close writer without exception")
    void shouldCloseWriter() throws IOException {
        // Act
        markdownReportWriter.close();

        // Assert
        verify(mockWriter).close();
    }

    @Test
    @DisplayName("Should throw LogWritingException when IOException occurs in close")
    void shouldThrowExceptionWhenCloseFails() throws IOException {
        // Arrange
        doThrow(new IOException("IO Error")).when(mockWriter).close();

        // Act & Assert
        assertThatThrownBy(() -> markdownReportWriter.close())
            .isInstanceOf(LogWritingException.class)
            .hasMessageContaining("Error closing report file");
    }
}
