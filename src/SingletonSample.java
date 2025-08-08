import common.Singleton;

public class SingletonSample {
    public static void main(String[] args) {
        System.out.print("SingletonSample!!");
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();

        s1.showMessage();

        // Verify it's the same instance
        System.out.println(s1 == s2); // true
    }
}
