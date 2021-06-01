# json-path-reader
A tool to read a json by the field's path
* When we meet a huge json string but we are only interested in a little part ,we can use a path to extract the part we need
* the path can be defined by a string,like 'a.b[0][*]','a' and 'b' represent the field's names,separated by the '.' ,the [number] represents the index of json array,[*]represents all the element of a json array
* This tool is built on GSON ,and can read the JsonElement from the target JsonElement
```java
 public void testFindElement() throws JsonPathParseException {
        A a = new A();
        a.arr = new A1[2];

        for (int i = 0; i < 2; i++) {

        a.arr[i] = new A1();
        a.arr[i].nums = new int[3];
        for (int j = 0; j < a.arr[i].nums.length; j++) {
        a.arr[i].nums[j] = j;
        }
        }

final JsonElement tree = new Gson().toJsonTree(a);

        System.out.println("tree = " + tree);

final JsonElement elements = Path.parse("arr[*].nums[1]").read(tree);

        System.out.println("elements = " + elements);

final int i= Path.parse("arr[1].nums[1]").read(tree).getAsInt();

        System.out.println("i = " + i);
        }

static class A {
    A1[] arr;
}

static class A1 {
    int[] nums;
}
```