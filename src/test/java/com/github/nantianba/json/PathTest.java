package com.github.nantianba.json;

import com.github.nantianba.json.layer.PathBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import junit.framework.TestCase;

public class PathTest extends TestCase {

    public void testParse() {
        final Path path = PathBuilder.builder()
                .append(0)
                .append("layer1")
                .append("layer2")
                .append("layer3")
                .build();

        System.out.println("path = " + path);
    }

    public void testTestToString() throws JsonPathParseException {
        System.out.println("Path.parse(\"[1]layer1[0].layer2.layer3[10]\") = " + Path.parse("[1]layer1[0].layer2.layer3[10]"));
        System.out.println("Path.parse(\"\") = " + Path.parse(""));
        System.out.println("Path.parse(\"l1[0].l2\") = " + Path.parse("l1[0].l2"));
        System.out.println("Path.parse(\"[2]\") = " + Path.parse("[2]"));
        System.out.println("Path.parse(\".l1\") = " + Path.parse(".l1"));
        System.out.println("Path.parse(\"[0].l1\") = " + Path.parse("[0].l1"));
        System.out.println("Path.parse(\"a.b.c\") = " + Path.parse("a.b.c"));
        System.out.println("Path.parse(\"_3 \") = " + Path.parse("_3 "));

        String[] errors = {
                "2",
                ".",
                ".[0].f",
                "[5",
                "5]",
                "5&",
                "2_d.df",
                "sd.54"
        };

        for (String error : errors) {
            try {
                Path.parse(error);
            } catch (JsonPathParseException e) {
                System.out.println(error + "\t:\t" + e.getMessage());
            }
        }
    }

    public void testFindElement() throws JsonPathParseException {
        A a = new A();
        a.arr = new A1[1];

        a.arr[0] = new A1();
        a.arr[0].nums = new int[3];
        for (int i = 0; i < a.arr[0].nums.length; i++) {
            a.arr[0].nums[i] = i;
        }

        final JsonElement tree = new Gson().toJsonTree(a);

        System.out.println("tree = " + tree);

        final int i = Path.parse("arr[0].nums[2]").read(tree).getAsInt();

        System.out.println("i = " + i);
    }

    static class A {
        A1[] arr;
    }

    static class A1 {
        int[] nums;
    }
}