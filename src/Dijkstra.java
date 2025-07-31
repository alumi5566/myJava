import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra {
    public static void main(String[] args) {
        PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        heap.add(new int[]{4, 1});
        heap.add(new int[]{4, 2});
        heap.add(new int[]{4, 3});
        heap.add(new int[]{0, 0});
        heap.add(new int[]{0, 1});
        while (heap.size() > 0) {
            int[] tmp = heap.poll();
            System.out.println(tmp[0] + ", " +tmp[1]);
        }
    }
}