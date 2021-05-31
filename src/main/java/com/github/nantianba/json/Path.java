package com.github.nantianba.json;

import com.github.nantianba.json.layer.Layer;
import com.github.nantianba.json.parser.PathParser;
import com.google.gson.JsonElement;
import lombok.Getter;

import java.util.List;

public class Path {
    @Getter
    private final List<Layer> layers;

    Path(List<Layer> layers) {
        this.layers = layers;
    }

    public static Path newPath(List<Layer> layers) {
        return new Path(layers);
    }

    public static Path parse(String path) throws JsonPathParseException {
        return PathParser.parse(path);
    }

    public JsonElement read(JsonElement element) {
        for (Layer layer : getLayers()) {
            if (element == null) {
                return null;
            }
            element = layer.read(element);
        }

        return element;
    }



    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for (Layer layer : getLayers()) {
            builder.append(layer.description());
        }

        if (builder.length() > 0 && builder.charAt(0) == '.') {
            builder.deleteCharAt(0);
        }

        return builder.toString();
    }
}
