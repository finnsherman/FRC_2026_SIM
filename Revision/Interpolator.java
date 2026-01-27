package Revision;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import static java.lang.Math.atan2;

import java.lang.invoke.ConstantBootstraps;

public class Interpolator {
    double graivty = Constants.gravitationalConstant;
    double increment = 0.2;
    double maxVelocityComponent = 6.7;

    double TX = Constants.targetX;
    double TY = Constants.targetY;
    double TZ = Constants.targetHeight;

    // At most all you should know to input is how fast the ROBOT is moving and where it is. 
    private record ShotInput(double px, double py, double vx, double vy) { };
    private record ShotCanidate(ShotInput input, double pitch, double yaw) { };

    public Interpolator() {
        for (double px = Constants.fieldMinX; px <= Constants.fieldMaxX; px += increment) {
            for (double py = Constants.fieldMinY; py <= Constants.fieldMaxY; py += increment) {
                double dx = TX - px;
                double dy = TY - py;
                double r = sqrt(pow(dx, 2) + pow(dy, 2));
                double toTargetYaw = atan2(dy, dx);
                double toTargetPitch = atan2(TZ, r);

            }
        }
    }

}
