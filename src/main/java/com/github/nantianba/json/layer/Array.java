package com.github.nantianba.json.layer;

import com.google.gson.JsonElement;

public class Array implements Layer {
    private final int index;

    public Array(int index) {
        this.index = index;
    }

    @Override
    public JsonElement read(JsonElement element) {
        return element.getAsJsonArray().get(index);
    }

    @Override
    public String description() {
        return "[" + index + "]";
    }
}
