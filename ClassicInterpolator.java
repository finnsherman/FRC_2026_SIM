import java.util.HashMap;
import java.util.Map;

public class ClassicInterpolator {

    private record result(double pitch, double velocity) {
    }

    Map<Double, result> lookupTable = new HashMap<>();
    double g = Constants.gravitationalConstant;
    double dy = Constants.targetHeight;
    double distance;

    public Map<Double, result> generate_table() {
        for (double distance = 1; distance <= 20; distance += 0.2) {
            for (double theta = 15; theta <= 45; theta += 1) {
                double velocity = Math.sqrt(Math.abs((Math.pow(distance, 2) * g) / (Math.pow(Math.cos(theta), 2) * 2 * (dy - distance * Math.tan(theta)))));
                lookupTable.put(Double.valueOf(distance), new result(theta, velocity));
            }
        }
        return lookupTable;
    }

    public static void main(String[] args) {
        System.out.print(new ClassicInterpolator().generate_table());
    }
}