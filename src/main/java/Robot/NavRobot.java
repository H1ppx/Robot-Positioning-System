package Robot;

import Socket.Rio;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Encoder;
import Vector.Vector2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.IOException;

public class NavRobot {

    private AHRS navx;
    private Encoder left, right;
    private double inchPerTick;

    private Rio rio;

    private double oldSec;
    private double oldDistance;
    private double oldHeading;
    private Vector2d position;

    public NavRobot(AHRS navx, Encoder left, Encoder right, double ticksPerInch) throws IOException {
        this.navx = navx;
        this.left = left;
        this.right = right;
        this.inchPerTick = 1/ticksPerInch;

        reset();
        rio = new Rio();
        rio.start();
    }

    public void position() throws IOException {
        double pastSec = System.currentTimeMillis()/1000.0-oldSec;
        if(pastSec > 0.25) {
            double distance = ((left.get()*inchPerTick +
                    right.get()*inchPerTick)/2);

            double angle = navx.getYaw() - oldHeading;
            double diffDistance = distance - oldDistance;
            SmartDashboard.putNumber("Heading", angle);
            Vector2d change = new Vector2d(diffDistance, angle);
            position = position.add(change);
            rio.sendData(position.x,position.y);

            oldSec = System.currentTimeMillis()/1000.0;
            oldDistance = distance;
            oldHeading = angle;
        }

    }

    public void reset(){
        oldSec = 0;
        oldDistance = 0;
        oldHeading = 0;
        position = new Vector2d(0,0);
    }

}
