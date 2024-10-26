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

class AsciidocReportWriterTest {

    @Mock
    private BufferedWriter mockWriter;

    @InjectMocks
    private AsciidocReportWriter asciidocReportWriter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        asciidocReportWriter = spy(new AsciidocReportWriter("dummyPath"));
        asciidocReportWriter.writer = mockWriter; // inject mock
    }

    @Test
    @DisplayName("Should write header in Asciidoc format")
    void shouldWriteHeader() throws IOException {
        // Act
        asciidocReportWriter.writeHeader("sampleFile");

        // Assert
        verify(mockWriter).write("= Log Analysis Report: sampleFile\n\n");
    }

    @Test
    @DisplayName("Should write metric in Asciidoc format")
    void shouldWriteMetric() throws IOException {
        // Act
        asciidocReportWriter.writeMetric("Total Requests", 100);

        // Assert
        verify(mockWriter).write("== Total Requests\n");
        verify(mockWriter).write("100\n\n");
    }

    @Test
    @DisplayName("Should write footer in Asciidoc format")
    void shouldWriteFooter() throws IOException {
        // Act
        asciidocReportWriter.writeFooter();

        // Assert
        verify(mockWriter).write("\n----\n\n");
        verify(mockWriter).write("Report generated automatically.");
    }

    @Test
    @DisplayName("Should throw LogWritingException when IOException occurs in header")
    void shouldThrowExceptionWhenWriteHeaderFails() throws IOException {
        // Arrange
        doThrow(new IOException("IO Error")).when(mockWriter).write(anyString());

        // Act & Assert
        assertThatThrownBy(() -> asciidocReportWriter.writeHeader("sampleFile"))
            .isInstanceOf(LogWritingException.class)
            .hasMessageContaining("Error writing header to report");
    }

    @Test
    @DisplayName("Should throw LogWritingException when IOException occurs in metric")
    void shouldThrowExceptionWhenWriteMetricFails() throws IOException {
        // Arrange
        doThrow(new IOException("IO Error")).when(mockWriter).write(anyString());

        // Act & Assert
        assertThatThrownBy(() -> asciidocReportWriter.writeMetric("Total Requests", 100))
            .isInstanceOf(LogWritingException.class)
            .hasMessageContaining("Error writing metric");
    }

    @Test
    @DisplayName("Should throw LogWritingException when IOException occurs in footer")
    void shouldThrowExceptionWhenWriteFooterFails() throws IOException {
        // Arrange
        doThrow(new IOException("IO Error")).when(mockWriter).write(anyString());

        // Act & Assert
        assertThatThrownBy(() -> asciidocReportWriter.writeFooter())
            .isInstanceOf(LogWritingException.class)
            .hasMessageContaining("Error writing report footer");
    }

    @Test
    @DisplayName("Should close writer without exception")
    void shouldCloseWriter() throws IOException {
        // Act
        asciidocReportWriter.close();

        // Assert
        verify(mockWriter).close();
    }

    @Test
    @DisplayName("Should throw LogWritingException when IOException occurs in close")
    void shouldThrowExceptionWhenCloseFails() throws IOException {
        // Arrange
        doThrow(new IOException("IO Error")).when(mockWriter).close();

        // Act & Assert
        assertThatThrownBy(() -> asciidocReportWriter.close())
            .isInstanceOf(LogWritingException.class)
            .hasMessageContaining("Error closing report file");
    }
}
