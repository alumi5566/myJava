package common;

public class Outer {
    private String message = "From Outer";

    // ✅ Static nested class
    public static class StaticNested {
        public void greet() {
            System.out.println("Hello from StaticNested class");
            // Cannot access 'message' directly
        }
    }

    // ✅ Non-static inner class
    public class Inner {
        public void greet() {
            System.out.println("Hello from Inner class");
            System.out.println("Accessing outer message: " + message);  // Can access outer instance variable
        }
    }
}
