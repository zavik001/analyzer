package backend.academy.loganalyzer.parser;

import backend.academy.loganalyzer.error.parsing.LogParsingException;
import backend.academy.loganalyzer.model.ParsedArguments;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArgumentParserTest {

    private final ArgumentParser argumentParser = new ArgumentParser();

    @Test
    @DisplayName("Should parse mandatory --path argument")
    void shouldParseMandatoryPathArgument() throws LogParsingException {
        // Arrange
        String[] args = {"--path", "logs/test.log"};

        // Act
        ParsedArguments parsedArguments = argumentParser.parse(args);

        // Assert
        assertThat(parsedArguments.paths()).contains("logs/test.log");
    }

    @ParameterizedTest
    @DisplayName("Should throw MissingArgumentException for missing mandatory arguments")
    @ValueSource(strings = {"--from", "--to", "--filter-field", "--filter-value"})
    void shouldThrowExceptionForMissingMandatoryArguments(String missingArg) {
        // Arrange
        String[] args = {missingArg, "someValue"};

        // Act & Assert
        assertThatThrownBy(() -> argumentParser.parse(args))
            .isInstanceOf(LogParsingException.class)
            .hasMessageContaining("The '--path' parameter is mandatory and must be specified.");
    }

    @Test
    @DisplayName("Should throw InvalidArgumentException if only --filter-field is specified without --filter-value")
    void shouldThrowExceptionIfFilterFieldWithoutFilterValue() {
        // Arrange
        String[] args = {"--path", "logs/test.log", "--filter-field", "statusCode"};

        // Act & Assert
        assertThatThrownBy(() -> argumentParser.parse(args))
            .isInstanceOf(LogParsingException.class)
            .hasMessageContaining("Both '--filter-field' and '--filter-value' must be specified together.");
    }

    @Test
    @DisplayName("Should throw InvalidArgumentException if only --filter-value is specified without --filter-field")
    void shouldThrowExceptionIfFilterValueWithoutFilterField() {
        // Arrange
        String[] args = {"--path", "logs/test.log", "--filter-value", "404"};

        // Act & Assert
        assertThatThrownBy(() -> argumentParser.parse(args))
            .isInstanceOf(LogParsingException.class)
            .hasMessageContaining("Both '--filter-field' and '--filter-value' must be specified together.");
    }

    @Test
    @DisplayName("Should parse optional arguments --from, --to, --format")
    void shouldParseOptionalArguments() throws LogParsingException {
        // Arrange
        String[] args = {"--path", "logs/test.log", "--from",
            "2024-08-01", "--to", "2024-08-31", "--format", "markdown"};

        // Act
        ParsedArguments parsedArguments = argumentParser.parse(args);

        // Assert
        assertThat(parsedArguments.paths()).contains("logs/test.log");
        assertThat(parsedArguments.fromDate()).isEqualTo("2024-08-01");
        assertThat(parsedArguments.toDate()).isEqualTo("2024-08-31");
        assertThat(parsedArguments.format()).isEqualTo("markdown");
    }

    @Test
    @DisplayName("Should parse filter arguments --filter-field and --filter-value together")
    void shouldParseFilterFieldAndFilterValueTogether() throws LogParsingException {
        // Arrange
        String[] args = {"--path", "logs/test.log", "--filter-field", "statusCode", "--filter-value", "404"};

        // Act
        ParsedArguments parsedArguments = argumentParser.parse(args);

        // Assert
        assertThat(parsedArguments.filterField()).isEqualTo("statusCode");
        assertThat(parsedArguments.filterValue()).isEqualTo("404");
    }

    @Test
    @DisplayName("Should throw LogParsingException for unsupported arguments")
    void shouldThrowExceptionForUnsupportedArguments() {
        // Arrange
        String[] args = {"--unsupported", "someValue"};
    
        // Act & Assert
        assertThatThrownBy(() -> argumentParser.parse(args))
            .isInstanceOf(LogParsingException.class)
            .hasMessageContaining("Unsupported argument");
    }
}
