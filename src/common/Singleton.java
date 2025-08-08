package common;

public class Singleton {
    private static volatile Singleton instance;
    private Singleton() {
        System.out.print("SampleClass Constructor !!");
    }
    public static Singleton getInstance(){
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
    public void showMessage() {
        System.out.println("Hello from Singleton!");
    }
}