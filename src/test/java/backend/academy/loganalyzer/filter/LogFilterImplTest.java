package backend.academy.loganalyzer.filter;

import backend.academy.loganalyzer.error.reading.date.InvalidDateException;
import backend.academy.loganalyzer.error.reading.filter.InvalidFilterException;
import backend.academy.loganalyzer.model.LogEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class LogFilterImplTest {

    @InjectMocks
    private LogFilterImpl logFilter;

    @Mock
    private LogEntry logEntry;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFilterLogEntryByTimeRange() throws InvalidFilterException {
        // Arrange
        when(logEntry.timestamp()).thenReturn("[17/May/2015:08:05:32 +0000]");
        
        // Act & Assert
        assertThat(logFilter.filter(logEntry, "2015-05-16", "2015-05-18", null, null)).isTrue();
        assertThat(logFilter.filter(logEntry, "2015-05-18", null, null, null)).isFalse();
        assertThat(logFilter.filter(logEntry, null, "2015-05-16", null, null)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
        "ipAddress, 93.180.71.3, true",
        "statusCode, 200, true",
        "statusCode, 404, false"
    })
    void shouldFilterLogEntryByField(String field, String value, boolean expectedResult) throws InvalidFilterException {
        // Arrange
        when(logEntry.ipAddress()).thenReturn("93.180.71.3");
        when(logEntry.statusCode()).thenReturn(200);
        
        // Act
        boolean result = logFilter.filter(logEntry, null, null, field, value);

        // Assert
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldFilterLogEntryByTimeAndField() throws InvalidFilterException {
        // Arrange
        when(logEntry.timestamp()).thenReturn("[17/May/2015:08:05:32 +0000]");
        when(logEntry.ipAddress()).thenReturn("93.180.71.3");

        // Act
        boolean result = logFilter.filter(logEntry, "2015-05-16", "2015-05-18", "ipAddress", "93.180.71.3");

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void shouldThrowInvalidFilterException_whenInvalidFieldProvided() {
        // Arrange
        when(logEntry.ipAddress()).thenReturn("93.180.71.3");

        // Act & Assert
        assertThatThrownBy(() -> logFilter.filter(logEntry, null, null, "invalidField", "value"))
            .isInstanceOf(InvalidFilterException.class)
            .hasMessageContaining("Invalid field for filtering");
    }

    @Test
    void shouldThrowInvalidDateException_whenInvalidDateProvided() {
        // Arrange
        when(logEntry.timestamp()).thenReturn("[17/May/2015:08:05:32 +0000]");

        // Act & Assert
        assertThatThrownBy(() -> logFilter.filter(logEntry, "invalid-date", null, null, null))
            .isInstanceOf(InvalidDateException.class)
            .hasMessageContaining("Invalid date for filtering: invalid-date");
    }
}
