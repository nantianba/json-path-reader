package com.github.nantianba.json.layer;

import com.google.gson.JsonElement;

public class Field implements Layer {
    private final String name;

    public Field(String name) {
        this.name = name;
    }

    @Override
    public JsonElement read(JsonElement element) {
        if (element.isJsonNull() || !element.isJsonObject()) {
            return null;
        }
        return element.getAsJsonObject().get(name);
    }

    @Override
    public String description() {
        return "." + name;
    }

    @Override
    public boolean isWildCard() {
        return false;
    }
}
