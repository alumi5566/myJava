// import lombok.AllArgsConstructor;
// import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

//@AllArgsConstructor
//@NoArgsConstructor
public class LC_CommonTrick {
    static void output(String title, int[] arr) {
        System.out.printf(title+" ");
        for (int a: arr) {System.out.printf(a + " ");}
        System.out.printf("\n");
    }
    static void outputArrayList(String title, ArrayList<Integer> arr) {
        System.out.printf(title+" ");
        for (int a: arr) {System.out.printf(a + " ");}
        System.out.printf("\n");
    }
    public static void main(String[] args) {
        System.out.println("LC_CommonTrick!");
        // 1 = sort int[] -> Arrays.sort()
        int[] arr1 = {2, 10, 4, 12, 6, 14};
        Arrays.sort(arr1);
        output("arr1:", arr1);
        // Comparator only exist in non-primitive arrays,
        // so you need to manually revert after Arrays.sort()

        // 2 = sort ArrayList 0>
        // Use Arrays.asList() to initialize ArrayList
        ArrayList<Integer> arr2 = new ArrayList<>(Arrays.asList(2, 10, 4, 12, 6, 14));
        Collections.sort(arr2);
        outputArrayList("arr2:", arr2);

        // Using Collections.reverseOrder() as comparator
        Collections.sort(arr2, Collections.reverseOrder());
        outputArrayList("arr2(reverse):", arr2);

        // Using list.sort()
        arr2 = new ArrayList<>(Arrays.asList(2, 10, 4, 12, 6, 14));
        arr2.sort(Collections.reverseOrder());
        outputArrayList("arr2(reverse):", arr2);

        // Using list.sort() and customized lambda
        arr2 = new ArrayList<>(Arrays.asList(2, 10, 4, 12, 6, 14));
        arr2.sort((a, b) -> b - a);
        outputArrayList("arr2(reverse):", arr2);
    }
}
