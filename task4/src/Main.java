
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;

public class Main {
    public static void main(String[] args) throws IOException {
        String numsPath = args[0];

        Path path = Paths.get(numsPath);
        try(Stream<String> numsStream = lines(path)) {
            List<Integer> nums = numsStream.toList().stream().map(Integer::parseInt).sorted().toList();
            int median = nums.get(nums.size() / 2);

            int moves = 0;
            for (int num : nums) {
                moves += Math.abs(num - median);
            }
            System.out.println(moves);
        }

    }
}