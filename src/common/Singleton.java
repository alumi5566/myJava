package common;

public class Singleton {
    // private so the only way to get the instance is by getInstance()
    // static to ensure this instance is shared across all classes
    // volatile to ensures visibility across threads and prevents instruction reordering.
    private static volatile Singleton instance;
    // private to prevents calling constructor from outside the class
    // no "new Singleton()" allowed
    private Singleton() {
        System.out.print("SampleClass Constructor !!");
    }
    // public to allows global access to the singleton instance
    // static so you can call it without needing an instance
    // -> Singleton.getInstance()
    public static Singleton getInstance(){
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    // This is the better way to ensure thread free
    public static Singleton getInstanceThreadFree() {
        // First check (non-synchronized) for performance
        if (instance == null) {
            // 🔐 Synchronize only if instance is null, to ensure only one thread creates it
            synchronized (Singleton.class) {
                // Double-check inside the lock for thread safety
                if (instance == null) {
                    instance = new Singleton(); // 🔧 Lazy initialization
                }
            }
        }
        return instance;
    }
    public void showMessage() {
        System.out.println("Hello from Singleton!");
    }
}