import java.util.ArrayList;

public class sim {
    public static void generateTable() {
        double C = 0.5588;
        double dZ = 2;
        double v_Range = 6.7056;
        double g = 9.81;
        ArrayList<double[]> result = new ArrayList<>();

        double vStep = 0.02;
        int vSteps = (int) (v_Range / vStep);

        for (int i = 0; i < vSteps; i++) {
            double v_X = i * vStep;
            for (int j = 0; j < vSteps; j++) {
                double v_Y = j * vStep;
                for (int k = 0; k < vSteps; k++) {
                    double v_Z = k * vStep;
                    if ((v_X + v_Y + v_Z) <= v_Range) {
                        continue;
                    }
                    double dXY = 1.0;
                    while (dXY < 18.288) {
                        double denominator = (v_X * v_X) + (v_Y * v_Y);
                        double theta = Math.atan2(
                            dZ - C + (g * ((dXY * dXY) / denominator)) / 2.0,
                            dXY
                        );
                        double phi = Math.atan2(v_Y, v_X);
                        result.add(new double[]{v_X, v_Y, v_Z, dXY, theta, phi});
                        dXY += 0.1;
                    }
                }
            }
        }
        // Print results
        for (double[] entry : result) {
            System.out.printf("v_X=%.2f, v_Y=%.2f, v_Z=%.2f, dXY=%.2f, theta=%.4f, phi=%.4f%n",
                entry[0], entry[1], entry[2], entry[3], entry[4], entry[5]);
        }
    }

    public static void main(String[] args) {
        generateTable();
    }
}
