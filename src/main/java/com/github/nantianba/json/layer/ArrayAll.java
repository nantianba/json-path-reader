package com.github.nantianba.json.layer;

import com.google.gson.JsonElement;

public class ArrayAll implements Layer {
    @Override
    public JsonElement read(JsonElement element) {
        return element.getAsJsonArray();
    }

    @Override
    public String description() {
        return "[*]";
    }

    @Override
    public boolean isWildCard() {
        return true;
    }
}
