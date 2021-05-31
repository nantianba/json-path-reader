package com.github.nantianba.json.parser;

import com.github.nantianba.json.JsonPathParseException;
import com.github.nantianba.json.Path;
import com.github.nantianba.json.layer.PathBuilder;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PathParser {
    public Path parse(String path) throws JsonPathParseException {
        final PathBuilder pathBuilder = PathBuilder.builder();

        final char[] chars = path.toCharArray();

        State state = new NextLayer();

        for (int i = 0; i < chars.length; i++) {
            final char c = chars[i];

            if (c == ' ') {
                continue;
            }

            state.process(c, i);

            if (state.isEnd()) {
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

    private void processOutPut(PathBuilder pathBuilder, State state) {
        final Output output = state.output();

        if (output != null) {
            if (output.isIndex) {
                pathBuilder.append(output.index);
            } else {
                pathBuilder.append(output.field);
            }
        }
    }
}
