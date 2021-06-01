package com.github.nantianba.json.parser;

import com.github.nantianba.json.JsonPathParseException;

/**
 * read a '*' and wait for ']'
 */
public class WildCardRead implements State {
    private State nextState;
    private boolean readParentheses;

    @Override
    public boolean canEnd() {
        return readParentheses;
    }

    @Override
    public boolean hasEnd() {
        return readParentheses;
    }

    @Override
    public Output output() {
        if (!hasEnd()) {
            return null;
        }
        final Output output = new Output();
        output.isWildCard = true;
        return output;
    }

    @Override
    public void process(char c, int index) throws JsonPathParseException {
        if (c == ']') {
            readParentheses = true;
            nextState = new NextLayer();
        } else {
            throw new JsonPathParseException("unexcepted char in index " + index + ":" + c);
        }
    }

    @Override
    public State nextState() {
        return nextState;
    }
}
