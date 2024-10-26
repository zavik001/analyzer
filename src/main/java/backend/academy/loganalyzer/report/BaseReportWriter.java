package backend.academy.loganalyzer.report;

import backend.academy.loganalyzer.error.writer.LogWritingException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Abstract class for writing reports, implementing basic writing logic.
 */
@Getter
@RequiredArgsConstructor
public abstract class BaseReportWriter implements IReportWriter {
    protected static final String DOUBLE_NEWLINE = "\n\n";
    protected BufferedWriter writer;
    protected final String filePath;

    /**
     * Initializes the {@link BufferedWriter} for writing to the file.
     *
     * @throws LogWritingException if an error occurs during writer creation
     */
    protected void initializeWriter() throws LogWritingException {
        if (this.writer == null) {
            try {
                this.writer = new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new LogWritingException("Error creating writer for file: " + filePath, e);
            }
        }
    }

    /**
     * Closes the report writer and releases any associated resources.
     */
    @Override
    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            throw new LogWritingException("Error closing report file", e);
        }
    }
}
