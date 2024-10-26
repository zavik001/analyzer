package backend.academy.loganalyzer;

import backend.academy.loganalyzer.manager.LogAnalysisManager;
import lombok.experimental.UtilityClass;

/**
 * Entry point for the log analysis application.
 * Initializes the LogAnalysisManager and triggers the log analysis process.
 */
@UtilityClass
public class Main {

    /**
     * Main method to start the log analysis.
     *
     * @param args Command-line arguments for configuring the analysis process, such as log paths and filters.
     */
    public static void main(String[] args) {
        // Initialize LogAnalysisManager with command-line arguments and report file path
        LogAnalysisManager manager = new LogAnalysisManager(args, "src/main/resources/log_analysis_report.");

        // Execute the log analysis
        manager.run();
    }
}
