import java.util.ArrayList;

public class StringStringBuilderStringBuffer {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("StringCmp!");

        // Java基本概念: String vs. StringBuilder vs. StringBuffer
        // - String是immutable，StringBuilder和StringBuffer是mutable
        // - String是thread-safe，
        //   StringBuilder是Not-thread-safe
        //   StringBuffer是thread-safe
        // - 如果你update String會很慢 (因為要create new object)
        //   update StringBuilder最快 (但是不同thread之間可能not-sync)
        //   update StringBuffer比String快，但是比StringBuilder慢 (因為要syncup不同thread之間)

        String s = "Hello"; // JVM allocate space in String Pool in Heap, s is the reference
        s += " World";  // Creates a new object in Heap, update s to reference this new space

        StringBuilder sb = new StringBuilder("Hello");
        sb.append(" World");  // Modifies the same object
        System.out.println(sb);  // Hello World

        StringBuffer sbf = new StringBuffer("Hello");
        sbf.append(" World");  // Modifies the same object, thread-safe
        System.out.println(sbf);  // Hello World

        // StringBuilder沒有locking比較快，StringBuffer有locking有concurrence protection
        // 見下例，multi-thread access StringBuilder
        StringBuilder sb_thread = new StringBuilder();
        Thread t1 = new Thread(() -> sb_thread.append("Hello "));
        Thread t2 = new Thread(() -> sb_thread.append("World"));
        t1.start();
        t2.start();
        // Wait for both threads to complete
        t1.join();
        t2.join();
        // 可能會得到 "HelloWorld", "WorldHello", "HeWorlldo"
        // 甚至可能導致crush — depending on thread scheduling.
        System.out.println("sb_thread: " + sb_thread);

        // 見下例，multi-thread access StringBuilder
        StringBuffer sbf_thread = new StringBuffer();
        Thread t3 = new Thread(() -> sbf_thread.append("Hello "));
        Thread t4 = new Thread(() -> sbf_thread.append("World"));
        t3.start();
        t4.start();
        // Wait for both threads to complete
        t3.join();
        t4.join();
        // 確保兩個thread不會互相干擾
        System.out.println("sbf_thread: " + sbf_thread);
    }
}
