package Revision;
import java.lang.invoke.ConstantBootstraps;

public class Interpolator {
    double graivty = Constants.gravitationalConstant;
    double targetHeight = Constants.targetHeight;
    double increment = 0.2;
    double maxVelocityComponent = 6.7;
    double maxPX = Constants.fieldMaxX;
    double maxPY = Constants.fieldMaxY;

    // At most all you should know to input is how fast the ROBOT is moving and where it is. 
    private record ShotInput(double px, double py, double vx, double vy);

}
