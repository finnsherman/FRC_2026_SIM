import java.util.HashMap;
import java.util.Map;

public class concept {

    private record InterpInput (double distance, double v_x, double v_y, double v_z) { };
    private record InterpOutput (double yaw, double pitch) { }; 

    double maxVelComponent = 6.7;
    double simIncrement = 0.2;
    double minDistance = 1;
    double maxDistance = 5;
    double height = 2;
    double C = 0.5588;
    double g = 9.81;
    double targetHeight = 2;
    
    int distanceEntries = Double.valueOf(Math.floor((maxDistance - minDistance) / simIncrement)).intValue();
    int velocityComponenEntries = Double.valueOf(Math.floor((2 * maxVelComponent) / simIncrement)).intValue();

    Map<InterpInput, InterpOutput> lookupTable = new HashMap<>();

    public void generateTable() {
        for (double velX = -maxVelComponent; velX <= maxVelComponent; velX += simIncrement ) {
            for (double velY = -maxVelComponent; velY <= maxVelComponent; velY += simIncrement ) {
                for (double velZ = -maxVelComponent; velZ <= maxVelComponent; velZ += simIncrement) {
                    if (Math.sqrt(Math.pow(velX, 2) + Math.pow(velY, 2) + Math.pow(velZ, 2)) <maxVelComponent) {
                        for (double distance = minDistance; distance <= maxDistance; distance += simIncrement) {
                            lookupTable.put(
                                new InterpInput(distance, velX, velY, velZ),
                                new InterpOutput(
                                    Math.atan2(height - C + (g * ((Math.pow(distance, 2))/((Math.pow(velX, 2)) + (Math.pow(velY, 2)))))/2, distance),
                                    Math.atan2(velY, velX)
                                ) 
                            );
                        }
                    }

                }
            }
        }
        System.out.print(lookupTable);
    }

    public static void main(String[] args) {
        new concept().generateTable();
    }
};
