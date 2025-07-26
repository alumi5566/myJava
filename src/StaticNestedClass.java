import common.Outer;

public class StaticNestedClass {
    public static void main(String[] args) {
        // 🔹 Static nested class: no Outer instance needed
        Outer.StaticNested staticNested = new Outer.StaticNested();
        staticNested.greet();

        // 🔹 Inner class: needs Outer instance
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        inner.greet();
    }
}
