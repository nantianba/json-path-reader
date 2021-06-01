# json-path-reader
A tool to read a json by the field's path
* When we meet a huge json string but we are only interested in a little part ,we can use a path to extract the part we need
* the path can be defined by a string,like 'a.b[0]','a' and 'b' represent the field's names,separated by the '.' ,the [0] represents the index of json array
* This tool is built on GSON ,and can read the JsonElement from the target JsonElement
```java
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
```
