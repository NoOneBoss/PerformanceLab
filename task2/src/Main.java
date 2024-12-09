import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static java.nio.file.Files.lines;

public class Main {
    public static void main(String[] args) throws IOException {
        String circlePath = args[0];
        String pointsPath = args[1];

        String[] circleData = lines(Paths.get(circlePath)).collect(Collectors.joining(" ")).split(" ");
        double circleX = Double.parseDouble(circleData[0]);
        double circleY = Double.parseDouble(circleData[1]);
        double radius = Double.parseDouble(circleData[2]);
        double radiusSquared = Math.pow(radius, 2);

        lines(Paths.get(pointsPath))
                .map(line -> line.split(" "))
                .map(coords -> new double[]{
                        Double.parseDouble(coords[0]),
                        Double.parseDouble(coords[1])
                })
                .mapToInt(point -> {
                    double distanceSquared = Math.pow(point[0] - circleX, 2) + Math.pow(point[1] - circleY, 2);

                    if (distanceSquared == radiusSquared) return 0;
                    else if (distanceSquared < radiusSquared) return 1;
                    else return 2;
                })
                .forEach(System.out::println);
    }
}