package Revision;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.toRadians;
import static java.lang.Math.floor;
import static java.lang.Math.floorMod;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.tan;

import java.lang.invoke.ConstantBootstraps;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    private record ShotInput(double px, double py, double vx, double  y) { };
    private record ShotCanidate(ShotInput input, double pitch, double yaw, double score) { };
    private record computedShotComponents(double speed, double sx, double sy, double sz) { };

    computedShotComponents computeShot(double r, double pitch, double yaw) {
        double stationaryLaunchSpeed = sqrt(abs((pow(r, 2) * graivty)
                                / (pow(cos(pitch), 2) * 2 * (TZ - r * tan(pitch)))));

                            double sz = stationaryLaunchSpeed * sin(pitch);

                            double sx = stationaryLaunchSpeed * cos(yaw);
                            double sy = stationaryLaunchSpeed * sin(yaw);
        return new computedShotComponents(stationaryLaunchSpeed, sx, sy, sz);
    }

    double computeDistance(double v, double pitch, double height) {
        double r = v * cos(pitch);
        double t = r / cos(pitch);

        return (
            -0.5 * graivty * pow(t, 2) + v * sin(pitch) * t + height
        );
    }

    double compute_dEpsilon_dPitch(double v, double pitch, double height, double delta) {
        return (computeDistance(v, pitch + delta, height) - computeDistance(v, pitch, height)) / delta;
    }

    double compute_dEpsilon_dSpeed(double v, double pitch, double height, double delta) {
        return (computeDistance(v + delta, pitch, height) - computeDistance(v, pitch, height)) / delta;
    }

    public Map<ShotInput, ShotCanidate> simulate() {
        Map<ShotInput, ShotCanidate> table = new HashMap<>();
        int canidateCount =  (int) floor((pitchMax - pitchMin) / increment);
        for (double px = Constants.fieldMinX; px <= Constants.fieldMaxX; px += increment) {
            for (double py = Constants.fieldMinY; py <= Constants.fieldMaxY; py += increment) {
                double dx = TX - px;
                double dy = TY - py;
                double r = sqrt(pow(dx, 2) + pow(dy, 2));
                double toTargetYawRadians = atan2(dy, dx);
                double toTargetPitchRadians = atan2(TZ, r);

                for (double theta = pitchMin; theta <= pitchMax; theta += increment) {
                    double thetaRadians = toRadians(theta);
                    
                }

                // vx and vy are ROBOT sppeeds including turret movements, not including speed used to shot robot. 

                for (double vx = -vrange; vx <= vrange; vx += increment) {
                    for (double vy = -vrange; vy <= vrange; vy += increment) {
                        ShotInput input = new ShotInput(px, py, vx, vy);
                        double vxy = sqrt(pow(vx, 2) + pow(vy, 2));

                        ShotCanidate[] canidateSpace = new ShotCanidate[canidateCount];

                        for (double theta = pitchMin; theta <= pitchMax; theta += increment) {
                            double thetaRads = toRadians(theta);

                            computedShotComponents shotComponents = computeShot(r, thetaRads, toTargetYawRadians);
                            double shotSpeed = shotComponents.speed;

                            double sx = shotComponents.sx;
                            double sy = shotComponents.sy;
                            double sz = shotComponents.sz;

                            double correctedYaw = atan2(shotComponents.sx - vx, shotComponents.sy - vy);

                            double nx = vx + sx;
                            double ny = vy + sy;
                            double nz = 0 + sz;

                            double nxy = sqrt(pow(nx, 2) + pow(ny, 2));

                            double timeToShot = r / nxy;

                            double dEpsilondPitch = compute_dEpsilon_dPitch(shotSpeed, theta, TZ, increment);
                            double dEpsilondSpeed = compute_dEpsilon_dSpeed(shotSpeed, theta, TZ, increment);

                            double score = timeToShot + shotSpeed + dEpsilondPitch + dEpsilondSpeed;

                            canidateSpace[(int) floor((theta - pitchMin) / increment)] = new ShotCanidate(input, theta, correctedYaw, score);
                        }

                        Arrays.sort(canidateSpace);
                        table.put(input, canidateSpace[0]);
                    }             
                }
            }
        }

        return table;
    }

}
