package com.github.nantianba.json.parser;

import com.github.nantianba.json.JsonPathParseException;

import java.util.LinkedList;

class ReadingField implements State {
    private LinkedList<Character> cache = new LinkedList<>();
    private State nextState;
    private boolean isEnd;

    @Override
    public boolean canEnd() {
        return !cache.isEmpty();
    }

    @Override
    public boolean isEnd() {
        return isEnd;
    }

    @Override
    public Output output() {
        if (cache.isEmpty()) {
            return null;
        } else {
            final Output output = new Output();
            output.isIndex = false;
            output.field = convertStr(cache);
            return output;
        }
    }

    private String convertStr(LinkedList<Character> cache) {
        StringBuilder builder = new StringBuilder();
        cache.forEach(builder::append);
        return builder.toString();
    }

    @Override
    public void process(char c, int index) throws JsonPathParseException {
        if (Character.isLetter(c)
                || c == '_') {
            nextState = this;
            cache.add(c);
        } else if (Character.isDigit(c)) {
            if (cache.isEmpty()) {
                throw new JsonPathParseException("unexcepted char in index " + index + ":" + c);
            }
            nextState = this;
            cache.add(c);
        } else if (c == '[') {
            if (!canEnd()) {
                throw new JsonPathParseException("unexcepted char in index " + index + ":" + c);
            }
            nextState = new ReadingIndex();
            isEnd = true;
        } else if (c == '.') {
            if (!canEnd()) {
                throw new JsonPathParseException("unexcepted char in index " + index + ":" + c);
            }
            nextState = new ReadingField();
            isEnd = true;
        } else {
            throw new JsonPathParseException("unexcepted char in index " + index + ":" + c);
        }
    }

    @Override
    public State nextState() {
        return nextState;
    }
}
