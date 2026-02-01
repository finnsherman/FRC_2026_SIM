import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Real {
    double dt = 0.02;
    double[] target_position = { 0, 0, 4 };
    Map<double[], double[]> table = new HashMap<>();

    public Map<double[], double[]> run() {
        for (double Px = -6; Px <= -1; Px += 0.2) {
            for (double Py = -6; Py <= -1; Py += 0.2) {
                for (double Vx = 0.5; Vx <= 15; Vx += 0.2) {
                    for (double Vy = 0.5; Vy <= 15; Vy += 0.2) {
                        for (double Vz = 0.5; Vz <= 15; Vz += 0.2) {
                            double[] position = { Px, Py, 1 };
                            double[] initalPosition = { Px, Py, 1};
                            double[] velocity = { Vx, Vy, Vz };
                            double[] initialVelocity = { Vx, Vy, Vz};
                            for (double t = 0; t <= 5; t += dt) {
                                if (isInRange(target_position, position)) {
                                    table.put(Arrays.copyOf(position, position.length),
                                            Arrays.copyOf(velocity, velocity.length));
                                    System.out.println("Intial, Position, Final Position, and Velocity:");
                                    System.out.println(Arrays.toString(initalPosition));
                                    System.out.println(Arrays.toString(position));
                                    System.out.printf(Arrays.toString(initialVelocity));
                                    System.out.printf("\n");

                                }
                                position[0] = position[0] + (velocity[0] * dt);
                                position[1] = position[1] + (velocity[1] * dt);
                                position[2] = position[2] + (velocity[2] * dt);
                                if (position[2] >= 0) {
                                    velocity[2] = velocity[2] - (9.81 * dt);
                                }
                            }
                        }
                    }
                }
            }
        }

        return table;
    }

    public boolean isInRange(double[] a1, double[] a2) {
        for (int i = 0; i < 3; i++) {
            if (!(Math.abs(a1[i] - a2[i]) < 0.02)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Real real = new Real();
        real.run();
        System.out.println(real);
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        for (double[] key : table.keySet()) {
            string.append(Arrays.toString(key));
            string.append(" -> ");
            string.append(Arrays.toString(table.get(key)));
            string.append("\n");
        }
        return string.toString();
    }
}
