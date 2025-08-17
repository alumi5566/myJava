import common.Singleton;

import java.util.Comparator;
import java.util.PriorityQueue;

public class HeapCmpSample {
    // Comparator<T> must return an int (1|0|-1)
    // 1 -> 前面的比較大
    // 0 -> 兩個相等
    // -1 -> 後面的比較大
    // using "y[1] - x[1]" works, but risking overflow
    // best practice is using
    // Integer.compare(y[1], x[1]); // larger y first
    static Comparator<int[]> lefty = (x, y) -> {
        // descending order ( min heap) for x-axis,
        // ascending order if x is a tie
        // 這也好記憶，一般的排序都是升序 (小的排前面)
        // 所以x[0] - y[0]就是正常升序
        // 這裡我們想要讓x[1]和y[1]是降序 (大的排前面) 所以反過來
        if (x[0] == y[0]) {return y[1] - x[1];}
        return x[0] - y[0];
    };
    // 再重申一次，用相減不是很好，最好用Integer.compare(y[1], x[1]); // larger y first
    static Comparator<int[]> lefty_v2 = (x, y) -> {
        if (x[0] == y[0]) {
            return Integer.compare(y[1], x[1]); // larger second first
        }
        return Integer.compare(x[0], y[0]);     // smaller first first
    };

    static Comparator<int[]> righty = (x, y) -> {
        if(x[0] == y[0]) { return x[1] - y[1];}
        return x[0] - y[0];
    };
    public static void main(String[] args) {
        System.out.print("HeapCmpSample!!\n");
        // left
        PriorityQueue<int[]> left = new PriorityQueue<>(lefty);
        PriorityQueue<int[]> left_v2 = new PriorityQueue<>(lefty_v2);
        // right
        PriorityQueue<int[]> right = new PriorityQueue<>(righty);
        int[][] buildings = {{2,9,10},{2,9,11},{3,7,15},{3,7,16},{5,12,12},{15,20,10},{19,24,8}};
        for (int[] building: buildings) {
            // left heap
            left.add(new int[]{building[0], building[2]});
            left_v2.add(new int[]{building[0], building[2]});
            // right heap
            right.add(new int[]{building[1], building[2]});
        }

        while (left.size() > 0) {
            int[] top = left.poll();
            int[] top_v2 = left_v2.poll();
            System.out.println("v1: (" + top[0] + ", " + top[1]+"), v2: ("+top_v2[0] + ", " + top_v2[1] + "}");
        }
        while (right.size() > 0) {
            int[] top = right.poll();
            System.out.println("right: {" + top[0] + ", " + top[1]+")");
        }

    }
}
