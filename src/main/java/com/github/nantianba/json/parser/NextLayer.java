package com.github.nantianba.json.parser;

import com.github.nantianba.json.JsonPathParseException;

class NextLayer implements State {
    private State nextState;

    @Override
    public boolean canEnd() {
        return true;
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public Output output() {
        return null;
    }

    @Override
    public void process(char c, int index) throws JsonPathParseException {
        if (c == '[') {
            nextState = new ReadingIndex();
        } else if (Character.isLetter(c) || c == '_') {
            nextState = new ReadingField();
            nextState.process(c, index);
        } else if (c == '.') {
            nextState = new ReadingField();
        } else {
            throw new JsonPathParseException("unexcepted char in index " + index + ":" + c);
        }
    }

    @Override
    public State nextState() {
        return nextState;
    }
}
