package backend.academy.loganalyzer.parser.handlers;

import backend.academy.loganalyzer.error.parsing.argument.MissingArgumentException;
import backend.academy.loganalyzer.model.ParsedArguments;

public class FilterValueArgumentHandler implements IArgumentHandler {

    @Override
    public boolean canHandle(String arg) {
        return "--filter-value".equals(arg);
    }

    @Override
    public int handle(String[] args, int index, ParsedArguments parsedArguments) throws MissingArgumentException {
        if (index >= args.length || args[index].startsWith("--")) {
            throw new MissingArgumentException("Missing value for parameter '--filter-value'.");
        }

        parsedArguments.filterValue(args[index]);
        return 1;
    }
}
