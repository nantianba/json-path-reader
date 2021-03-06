package com.github.nantianba.json.parser;

import com.github.nantianba.json.JsonPathParseException;
import com.github.nantianba.json.Path;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PathParser {
    public Path parse(String path) throws JsonPathParseException {
        final Path.PathBuilder pathBuilder = Path.PathBuilder.builder();

        final char[] chars = path.toCharArray();

        State state = new NextLayer();

        for (int i = 0; i < chars.length; i++) {
            final char c = chars[i];

            if (c == ' ') {
                continue;
            }

            state.process(c, i);

            if (state.hasEnd()) {
                processOutPut(pathBuilder, state);
            }

            state = state.nextState();
        }

        if (state.canEnd()) {
            processOutPut(pathBuilder, state);
        } else {
            throw new JsonPathParseException("expression not complete");
        }

        return pathBuilder.build();
    }

    private void processOutPut(Path.PathBuilder pathBuilder, State state) throws JsonPathParseException {
        final Output output = state.output();

        if (output != null) {
            if (output.isIndex) {
                pathBuilder.append(output.index);
            } else if (output.isWildCard) {
                pathBuilder.appendWildCard();
            } else {
                pathBuilder.append(output.field);
            }
        }
    }
}
