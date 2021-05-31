package com.github.nantianba.json.layer;

import com.github.nantianba.json.Path;

import java.util.LinkedList;
import java.util.List;

public class PathBuilder {
    private final List<Layer> layers = new LinkedList<>();

    public static PathBuilder builder() {
        return new PathBuilder();
    }

    public Path build() {
        return Path.newPath(layers);
    }

    public PathBuilder append(int index) {
        layers.add(new Array(index));

        return this;
    }

    public PathBuilder append(String name) {
        layers.add(new Field(name));

        return this;
    }
}
