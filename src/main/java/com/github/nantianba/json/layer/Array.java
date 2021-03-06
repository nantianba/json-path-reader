package com.github.nantianba.json.layer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class Array implements Layer {
    private final int index;

    public Array(int index) {
        this.index = index;
    }

    @Override
    public JsonElement read(JsonElement element) {
        if (element.isJsonNull()|| !element.isJsonArray()) {
            return null;
        }
        final JsonArray array = element.getAsJsonArray();

        if (array.size() <= index) {
            return null;
        }
        return array.get(index);
    }

    @Override
    public String description() {
        return "[" + index + "]";
    }

    @Override
    public boolean isWildCard() {
        return false;
    }
}
