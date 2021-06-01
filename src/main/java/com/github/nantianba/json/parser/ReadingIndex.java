package com.github.nantianba.json.parser;

import com.github.nantianba.json.JsonPathParseException;

import java.util.Iterator;
import java.util.LinkedList;

class ReadingIndex implements State {
    private LinkedList<Character> cache = new LinkedList<>();
    private State nextState;
    private boolean isEnd;

    @Override
    public boolean canEnd() {
        return false;
    }

    @Override
    public boolean hasEnd() {
        return isEnd;
    }

    @Override
    public Output output() {
        if (cache.isEmpty()) {
            return null;
        } else {
            final Output output = new Output();
            output.isIndex = true;
            output.index = convertInt(cache);
            return output;
        }
    }

    private int convertInt(LinkedList<Character> cache) {
        int ans = 0;
        final Iterator<Character> iterator = cache.descendingIterator();

        while (iterator.hasNext()) {
            ans *= 10;
            ans += iterator.next() - '0';
        }
        return ans;
    }

    @Override
    public void process(char c, int index) throws JsonPathParseException {
        if (Character.isDigit(c)) {
            cache.add(c);
            nextState = this;
        }else if(c=='*'){
            if (!cache.isEmpty()) {
                throw new JsonPathParseException("unexcepted char in index " + index + ":" + c);
            }
            nextState=new WildCardRead();
        }
        else if (c == ']') {
            isEnd = true;
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
