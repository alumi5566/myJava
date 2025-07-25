import common.ImmutablePerson;

// Java requires that the file name matches the public class name (Main.java here).
public class Main {
    // #1
    // This is the entry point for every standalone Java application.
    // The Java Virtual Machine (JVM) looks for this exact signature to start your program.
    // It is public since JVM need to all this method — it must be accessible from outside the class.
    // Static 關鍵字意味著我們在不創建類的新對象的情況下使用此方法。
    public static void main(String[] args) {
        System.out.println("Hello world!");
        // #2 Difference between "equal()" and "=="
        // equal() is a method, == is an operator
        // equal() -> Value Equality (Compares the actual content of the objects.)
        // == -> Reference Equality (two reference point direct to the same object in memory)
        // Use .equals() when comparing object contents, and == when comparing references or primitives.
        String a = new String("hello");
        String b = new String("hello");
        System.out.println("a == b: " + (a == b));         // false (different objects)
        System.out.println("a.equals(b): " + a.equals(b)); // true (same content)

        String c = "world";
        String d = "world";
        System.out.println("c == d: " + (c == d));         // true (same string pool reference)
        System.out.println("c.equals(d): " + c.equals(d)); // true (same content)

        Integer x = 1000;
        Integer y = 1000;
        System.out.println("x == y: " + (x == y));         // false (outside cache range)
        System.out.println("x.equals(y): " + x.equals(y)); // true (same value)

        int m = 1000;
        int n = 1000;
        System.out.println("m == m: " + (m == n));         // false (outside cache range)
//        System.out.println("m.equals(n): " + m.equals(n)); // error, Primitive doesn't has equal() method

        // #3 immutable
        // You cannot update immutable object after create it
        // check common/ImmutablePerson, which has a "final" annotation
        ImmutablePerson p1 = new ImmutablePerson("Alice", 30);
        System.out.println(p1.getName() + ", " + p1.getAge());
        // p1.name = "Bob";         // Compile error
        // p1.setAge(35);           // No setter method
        // 有一個有趣的點是如果你去修改immutable，會得到一個新對象（clone）並在創建時更改此clone
        // 嘗試修改Intefer (immutable)
        Integer aa = 5;
        aa = aa + 1;
        System.out.println(a); // 6 — but it's a new Integer object!
        // 嘗試修改String (immutable)
        String bb = "CY";
        bb = "VV";
        System.out.println(bb); // VV - 但是bb一開始指向"CY"，現在指向"VV"，"CY"依然沒有被修改

        // #4 以下三行code，總共創建了幾個object?
        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = "Hello";
        // 只有一個，Java有一個String constant pool (intern pool)
        // 第一個s1在pool創建了一個String object，然後s1指向這個object
        // s2和s3都指向這個String object

        // #5 以下一行code，總共創建了幾個object?
        String s = new String("Hello");
        // 兩個，一個String object "Hello"在String constant pool
        // 另外一個在Heap裡面 new String("Hello")
        // 第二個object是第一個的copy
        // 所以下面這一個例子很有趣
        String aaa = "Hello";              // Uses the string pool.
        String bbb = new String("Hello");  // Creates new object in heap.

        System.out.println(aaa == bbb);      // false (different memory locations)
        System.out.println(aaa.equals(bbb)); // true  (same content)


    }
}