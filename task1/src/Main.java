import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);

        List<Integer> circularList = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            circularList.add(i);
        }

        List<Integer> path = new ArrayList<>();
        int currentIndex = 0;

        do {
            path.add(circularList.get(currentIndex));
            currentIndex = (currentIndex + m - 1) % n;
        } while (currentIndex != 0);

        for (int num : path) {
            System.out.print(num);
        }
    }
}