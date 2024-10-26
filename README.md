# Log Analyzer

This is a log analysis tool developed as a homework assignment for the [Backend Academy 2024 by Tinkoff](https://edu.tinkoff.ru/), implemented in Java.

## Project Description

The analyzer reads log files from various sources (local files or URLs), analyzes their content for patterns and metrics, and outputs reports in Markdown or Asciidoc formats. It supports flexible filtering by date range and other criteria, providing detailed statistics on log data.

## Key Features

- **Log Reading**: Reads logs from file paths or URLs.
- **Filtering**: By date range, fields, and values (e.g., IPs, response codes).
- **Metrics Calculation**: Reports on request count, response code distribution, unique IPs, etc.
- **Formatted Reporting**: Markdown or Asciidoc structured reports.
- **Error Handling**: Custom exceptions for structured error management.
- **Command-Line Parser**: For flexible, customizable analysis configurations.

## Design and Architecture

The project follows **SOLID principles** and uses design patterns for modularity and scalability:

- **Factory Pattern**: For creating log readers and report writers (Markdown, Asciidoc).
- **Chain of Responsibility**: For extensible argument parsing.

## Testing

Testing covers functionality, interactions, and security, using:

- **Unit Tests** (JUnit 5, Mockito): For isolated component testing.
- **Integration Tests**: Verifies inter-component functionality.
- **Security Tests**: Ensures SSRF prevention in URL handling.

## Tools and Libraries

- **Libraries**: JUnit 5 for unit and parameterized tests, Mockito for mocking, AssertJ for fluent assertions, Lombok for boilerplate code generation.
- **Build Tools**: Maven with Checkstyle, Modernizer, SpotBugs, and PMD checks.

## How to Run the Analyzer

```bash
git clone https://github.com/zavik001/loganalyzer.git
cd loganalyzer
./mvnw clean verify
mvn exec:java -Dexec.mainClass="backend.academy.loganalyzer.Main" -Dexec.args="--path <log-path> [--from <start-date: yyyy-MM-dd>] [--to <end-date: yyyy-MM-dd>] [--filter-field <field> --filter-value <value>] [--format <output-format>]"
