package backend.academy.loganalyzer.parser.handlers;

import backend.academy.loganalyzer.error.parsing.argument.MissingArgumentException;
import backend.academy.loganalyzer.model.ParsedArguments;
import java.util.ArrayList;
import java.util.List;

public class PathArgumentHandler implements IArgumentHandler {

    @Override
    public boolean canHandle(String arg) {
        return "--path".equals(arg);
    }

    @Override
    public int handle(String[] args, int index, ParsedArguments parsedArguments) throws MissingArgumentException {
        int size = args.length - index;
        List<String> paths = new ArrayList<>(size);
        int i = index;

        while (i < args.length && !args[i].startsWith("--")) {
            paths.add(args[i++]);
        }

        if (paths.isEmpty()) {
            throw new MissingArgumentException("The '--path' parameter requires at least one path value.");
        }

        parsedArguments.paths(paths);
        return i - index;
    }
}
