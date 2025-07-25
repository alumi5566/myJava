class Parent_Private {
    private void greet() { System.out.println("Hello from Parent"); }
    public void callGreet() { greet();}
}
class Child_private extends Parent_Private {
    private void greet() { System.out.println("Hello from Child");}
}

class Parent_public {
    public void greet() { System.out.println("Hello from Parent"); }
    public void callGreet() { greet();}
}
class Child_public extends Parent_public {
    public void greet() { System.out.println("Hello from Child");}
}

public class OverridePrivate {
    public static void main(String[] args) {
        // 說明private method無法被override
        // Child繼承Parent，試圖override private method()
        Child_private child_pri = new Child_private();
        // 如果可以override，Child的callGreet()應該會呼叫override的greet()，然後輸出"Hello from Child"
        // 結果是輸出"Hello from Parent"
        System.out.println("Try override private");
        child_pri.callGreet();

        // 試圖override public method = 標準的多形(polymorphism)
        Child_public child_pub = new Child_public();
        System.out.println("Try override public");
        // 結果是輸出"Hello from Child"
        child_pub.callGreet();
    }
}
