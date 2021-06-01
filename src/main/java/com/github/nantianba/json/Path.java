package com.github.nantianba.json;

import com.github.nantianba.json.layer.Array;
import com.github.nantianba.json.layer.ArrayAll;
import com.github.nantianba.json.layer.Field;
import com.github.nantianba.json.layer.Layer;
import com.github.nantianba.json.parser.PathParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Path {
    private final List<Layer> layers;

    Path(List<Layer> layers) {
        this.layers = layers;
    }

    public static Path parse(String path) throws JsonPathParseException {
        return PathParser.parse(path);
    }

    public JsonElement read(JsonElement element) {
        List<JsonElement> elements = Collections.singletonList(element);
        boolean isArray = false;

        for (Layer layer : this.layers) {
            if (elements.size() == 0) {
                return null;
            }
            List<JsonElement> thisLayerData = new LinkedList<>();
            for (JsonElement e : elements) {
                final JsonElement readElement = layer.read(e);
                if (readElement == null) {
                    continue;
                }
                if (layer.isWildCard()) {
                    isArray = true;
                    for (JsonElement arrayElement : readElement.getAsJsonArray()) {
                        thisLayerData.add(arrayElement);
                    }
                } else {
                    thisLayerData.add(readElement);
                }
            }
            elements = thisLayerData;
        }

        return isArray
                ? elements.stream().collect(JsonArray::new, JsonArray::add, JsonArray::addAll)
                : elements.get(0);
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for (Layer layer : this.layers) {
            builder.append(layer.description());
        }

        if (builder.length() > 0 && builder.charAt(0) == '.') {
            builder.deleteCharAt(0);
        }

        return builder.toString();
    }

    public static class PathBuilder {
        private final List<Layer> layers = new LinkedList<>();
        private boolean hasWildCard = false;

        public static PathBuilder builder() {
            return new PathBuilder();
        }

        public Path build() {
            return new Path(layers);
        }

        public PathBuilder append(int index) {
            layers.add(new Array(index));

            return this;
        }

        public PathBuilder append(String name) {
            layers.add(new Field(name));

            return this;
        }

        public PathBuilder appendWildCard() throws JsonPathParseException {
            if (hasWildCard) {
                throw new JsonPathParseException("wildcard can only have one");
            }
            layers.add(new ArrayAll());
            hasWildCard = true;

            return this;
        }
    }
}
