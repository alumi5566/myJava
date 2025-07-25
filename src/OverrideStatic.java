class Parent_static {
    static void greet() { System.out.println("Static Hello from Parent"); }
}

class Child_static extends Parent_static {
    static void greet() { System.out.println("Static Hello from Child"); }
}
public class OverrideStatic {
    public static void main(String[] args) {
        // 由於static method是在compile時，與程式一起載進記憶體而不是在執行時載進記憶體
        // static method只看reference type, 與object type無關
        // 所以下面這個例子不會呼叫繼承的Child_static.greet()
        // 而是根據reference的Parent_static.greet()
        Parent_static p_static = new Child_static();
        p_static.greet();   // Output: Static Hello from Parent

        // 下面就很單純，都是呼叫Child_static的greet()
        Child_static c_static = new Child_static();
        c_static.greet();   // Output: Static Hello from Child
        Child_static.greet();   // Output: Static Hello from Child
    }
}
