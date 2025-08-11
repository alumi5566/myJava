import common.Singleton;

import java.util.ArrayList;
import java.util.List;

public class NonAtomicIncrementSample {
    static int counter = 0;
    public static void main(String[] args) throws InterruptedException {
        System.out.print("NonAtomicIncrementSample!!");
        int threads = 20;
        int itersPerThread = 20000;
        List<Thread> threadList = new ArrayList<>();

        Runnable task = () -> {
            for (int i=0;i<itersPerThread;i++) {
                counter++;
            }
        };
        for (int t=0;t<threads;t++) {
            Thread th = new Thread(task);
            threadList.add(th);
            th.start();
        }
        for (Thread th:threadList) {
            th.join();
        }
        int expected = threads * itersPerThread;
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + counter);
        //        NonAtomicIncrementSample!!Expected: 400000
        //        Actual:   163006
    }
}
