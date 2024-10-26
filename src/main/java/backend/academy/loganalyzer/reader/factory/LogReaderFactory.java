package backend.academy.loganalyzer.reader.factory;

import backend.academy.loganalyzer.reader.FileLogReader;
import backend.academy.loganalyzer.reader.ILogReader;
import backend.academy.loganalyzer.reader.UrlLogReader;
import lombok.experimental.UtilityClass;

/**
 * Factory class for creating instances of {@link ILogReader} based on the path format.
 * Determines whether to return a {@link FileLogReader} or a {@link UrlLogReader}
 * based on the provided path.
 */
@UtilityClass
public class LogReaderFactory {

    /**
     * Returns an appropriate {@link ILogReader} instance based on the path.
     * If the path starts with "http://" or "https://", an instance of {@link UrlLogReader} is returned.
     * Otherwise, a {@link FileLogReader} instance is returned.
     *
     * @param path the path or URL to the log source.
     * @return an instance of {@link ILogReader} suitable for reading logs from the specified path.
     */
    public static ILogReader getLogReader(String path) {
        if (path.startsWith("http://") || path.startsWith("https://")) {
            return new UrlLogReader();
        } else {
            return new FileLogReader();
        }
    }
}
