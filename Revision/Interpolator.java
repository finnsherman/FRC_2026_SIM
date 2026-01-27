package Revision;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.toRadians;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.tan;

import java.lang.invoke.ConstantBootstraps;

public class Interpolator {
    double graivty = Constants.gravitationalConstant;
    double increment = 0.2;
    double maxVelocityComponent = 6.7;

    double TX = Constants.targetX;
    double TY = Constants.targetY;
    double TZ = Constants.targetHeight;

    double vrange = Constants.velocityRange;

    double pitchMin = Constants.minPitch;
    double pitchMax = Constants.maxPitch;

    // At most all you should know to input is how fast the ROBOT is moving and where it is. 
    private record ShotInput(double px, double py, double vx, double vy) { };
    private record ShotCanidate(ShotInput input, double pitch, double yaw) { };

    public Interpolator() {
        for (double px = Constants.fieldMinX; px <= Constants.fieldMaxX; px += increment) {
            for (double py = Constants.fieldMinY; py <= Constants.fieldMaxY; py += increment) {
                double dx = TX - px;
                double dy = TY - py;
                double r = sqrt(pow(dx, 2) + pow(dy, 2));
                double toTargetYawRadians = atan2(dy, dx);
                double toTargetPitchRadians = atan2(TZ, r);

                // vx and vy are ROBOT sppeeds including turret movements, not including speed used to shot robot. 

                for (double vx = -vrange; vx <= vrange; vx += increment) {
                    for (double vy = -vrange; vy <= vrange; vy += increment) {
                        ShotInput input = new ShotInput(px, py, vx, vy);

                        for (double theta = pitchMin; theta <= pitchMax; theta += increment) {
                            double thetaRads = toRadians(theta);

                            // distance(r) is supposed to by XY distance right?
                            double stationaryLaunchSpeed = sqrt(abs((pow(r, 2) * graivty)
                        / (pow(cos(thetaRads), 2) * 2 * (TZ - r * Math.tan(thetaRads)))));

                        }
                    }
                }
            }
        }
    }

}
