
// Multi-Interface implementation sample 1: same name variable
interface Inter1 {
    int value = 10;
}
interface Inter2 {
    int value = 20;
}
class Father{
    int value = 30;
}
class A extends Father implements Inter1,Inter2{
    int value = 40;
}

// Multi-Interface implementation sample 2: same name method()
interface X{
    void method();
}
interface Y{
    void method();
    int method(int a);
}
class MyClass implements X, Y{
    public int method(int a) {
        System.out.println("MyClass.method(int a)");
        return -1;
    }
    public void method() {
        System.out.println("MyClass.method()");
    }
}

public class MultiInterfaceSample {
    public static void main(String[] args) {
        A a = new A();
        System.out.println( Inter1.value );  // 以介面名稱存取，因為是static修飾
        System.out.println( Inter2.value );  // 同上
        System.out.println( ((Father)a).value ); // 先把物件a轉型成該父類別，再存取
        System.out.println( a.value );  // 直接以物件存取
    }
}
