// import lombok.AllArgsConstructor;
// import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.PriorityQueue;

//@AllArgsConstructor
//@NoArgsConstructor
public class LC_StreamingMedian {
    // Max-heap for the smaller half
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    // Min-heap for the larger half
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    private int median = -1;
    public void insert(int num) {
        maxHeap.add(num);
        int tmp = maxHeap.poll();
        minHeap.add(tmp);
        tmp = minHeap.poll();
        if (maxHeap.size() == minHeap.size()) {
            maxHeap.add(tmp);
            median = tmp;
        } else {
            minHeap.add(tmp);
            median = maxHeap.peek();
        }
    }
    public void getMedian() {
        System.out.println(median);
    }

    public static void main(String[] args) {
        System.out.println("StreamingMedian!");
        LC_StreamingMedian sm = new LC_StreamingMedian();
        int[] input = {1, 100, 50, 10, 70, 20, 30, 80, 60, 90, 5, 40, 25};
        for (int num : input) {
            sm.insert(num);
            sm.getMedian();
        }
    }
}
