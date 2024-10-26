package backend.academy.loganalyzer.parser;

import backend.academy.loganalyzer.error.parsing.LogParsingException;
import backend.academy.loganalyzer.error.parsing.argument.InvalidArgumentException;
import backend.academy.loganalyzer.error.parsing.argument.MissingArgumentException;
import backend.academy.loganalyzer.model.ParsedArguments;
import backend.academy.loganalyzer.parser.handlers.FilterFieldArgumentHandler;
import backend.academy.loganalyzer.parser.handlers.FilterValueArgumentHandler;
import backend.academy.loganalyzer.parser.handlers.FormatArgumentHandler;
import backend.academy.loganalyzer.parser.handlers.FromArgumentHandler;
import backend.academy.loganalyzer.parser.handlers.IArgumentHandler;
import backend.academy.loganalyzer.parser.handlers.PathArgumentHandler;
import backend.academy.loganalyzer.parser.handlers.ToArgumentHandler;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * The {@code ArgumentParser} class is responsible for parsing command-line arguments
 * for the log analyzer application. It uses a chain of responsibility pattern
 * to delegate the handling of specific arguments to registered handlers.
 *
 * <p>This class validates the parsed arguments to ensure that mandatory parameters
 * are provided and that related parameters are specified together.</p>
 */
@Slf4j
public class ArgumentParser {

    // List of registered argument handlers
    private final List<IArgumentHandler> handlers = List.of(
            new PathArgumentHandler(),
            new FromArgumentHandler(),
            new ToArgumentHandler(),
            new FilterFieldArgumentHandler(),
            new FilterValueArgumentHandler(),
            new FormatArgumentHandler()
    );

    /**
     * Parses the given array of command-line arguments.
     *
     * @param args the array of command-line arguments
     * @return a {@link ParsedArguments} object containing the parsed values
     * @throws LogParsingException if an error occurs while processing the arguments
     */
    public ParsedArguments parse(String[] args) throws LogParsingException {
        ParsedArguments parsedArguments = new ParsedArguments();

        try {
            for (int i = 0; i < args.length;) {
                //log.debug("Processing argument: {}", args[i]);
                boolean handled = false;

                // Iterate through registered handlers to find one that can handle the argument
                for (IArgumentHandler handler : handlers) {
                    if (handler.canHandle(args[i])) {
                        // log.debug(
                        //     "Handler {} will process argument: {}", handler.getClass().getSimpleName(), args[i]);

                        int increment = handler.handle(args, i + 1, parsedArguments);
                        i += increment + 1;
                        handled = true;
                        break;
                    }
                }

                // If no handler could process the argument, throw an exception
                if (!handled) {
                    //log.error("Unsupported argument encountered: {}", args[i]);
                    throw new InvalidArgumentException("Unsupported argument: " + args[i]);
                }
            }

            // Validate the parsed arguments after processing all input
            validateParsedArguments(parsedArguments);
            //log.info("Argument parsing completed successfully");

        } catch (InvalidArgumentException | MissingArgumentException e) {
            //log.error("Error during argument parsing: {}", e.getMessage());
            throw new LogParsingException("Error processing arguments: " + e.getMessage(), e);
        }

        //logger.debug("Parsed arguments: {}", parsedArguments);
        return parsedArguments;
    }

    /**
     * Validates the parsed arguments to ensure all mandatory parameters are present
     * and that related parameters are specified correctly.
     *
     * @param parsedArguments the parsed arguments to validate
     * @throws InvalidArgumentException if validation fails due to missing or invalid arguments
     */
    private void validateParsedArguments(ParsedArguments parsedArguments) throws InvalidArgumentException {
        if (parsedArguments.paths() == null || parsedArguments.paths().isEmpty()) {
            //log.error("Validation failed: missing mandatory '--path' argument");
            throw new InvalidArgumentException("The '--path' parameter is mandatory and must be specified.");
        }

        if ((parsedArguments.filterField() != null && parsedArguments.filterValue() == null)
                || (parsedArguments.filterField() == null && parsedArguments.filterValue() != null)) {
            //log.error("Validation failed: '--filter-field' and '--filter-value' must be specified together");
            throw new InvalidArgumentException(
                    "Both '--filter-field' and '--filter-value' must be specified together.");
        }

        //log.debug("Validation of parsed arguments passed");
    }
}
