package com.github.nantianba.json.layer;

import com.google.gson.JsonElement;

public interface Layer {
    JsonElement read(JsonElement element);

    String description();
}
