package backend.academy.loganalyzer.parser.handlers;

import backend.academy.loganalyzer.error.parsing.LogParsingException;
import backend.academy.loganalyzer.model.ParsedArguments;

/**
 * The {@code IArgumentHandler} interface defines the contract for handling
 * command-line arguments in the log analyzer application. Implementing
 * classes should provide specific logic for processing different types of
 * arguments.
 */
public interface IArgumentHandler {

    /**
     * Determines if this handler can process the specified argument.
     *
     * @param arg the argument to check
     * @return {@code true} if this handler can handle the argument;
     *         {@code false} otherwise
     */
    boolean canHandle(String arg);

    /**
     * Handles the specified argument and updates the provided
     * {@link ParsedArguments} object.
     *
     * @param args the array of command-line arguments
     * @param index the index of the argument to handle
     * @param parsedArguments the {@link ParsedArguments} object to update
     * @return the number of arguments processed by this handler
     * @throws LogParsingException if an error occurs while handling the argument
     */
    int handle(String[] args, int index, ParsedArguments parsedArguments) throws LogParsingException;
}
