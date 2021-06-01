package com.github.nantianba.json.parser;

import com.github.nantianba.json.JsonPathParseException;

interface State {
    boolean canEnd();

    boolean hasEnd();

    Output output();

    void process(char c, int index) throws JsonPathParseException;

    State nextState();
}
