
public class Interpolator {
    double gravity = Constants.gravitationalConstant;
    double targetHeight = Constants.targetHeight;
    double delta = 0.2;
    double maxVelComponent = 6.7;

    double square(double x) {
        return Math.pow(x, 2);
    };

    private record input(double px, double py, double vx, double vy) {};
    private record something(input a, double pitch, double yaw, double shootVelocity) {};
    public Interpolator() {

        for (double distance = 1; distance <= 20; distance += delta) {
            for (double theta = 15; theta <= 45; theta += delta) {
                double Theta = Math.toRadians(theta);
                double requiredStationaryVelocity = Math.sqrt(Math.abs((Math.pow(distance, 2) * gravity)
                        / (Math.pow(Math.cos(Theta), 2) * 2 * (targetHeight - distance * Math.tan(Theta)))));

                for (double velX = -maxVelComponent; velX <= maxVelComponent; velX += delta) {
                    for (double velY = -maxVelComponent; velY <= maxVelComponent; velY += delta) {
                        for (double velZ = -maxVelComponent; velZ <= maxVelComponent; velZ += delta) {
                            if (Math.sqrt(
                                    Math.pow(velX, 2) + Math.pow(velY, 2) + Math.pow(velZ, 2)) < maxVelComponent) {
                                        circumstance potentialCircumstance = new circumstance(velX, velY, velZ, distance);
                                        
                            }
                        }
                    }
                }
            }
        }
    }
}
