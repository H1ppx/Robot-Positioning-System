package Robot;

import Socket.Server;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Encoder;
import Vector.Vector2d;

import java.io.IOException;

public class NavRobot {

    private AHRS navx;
    private Encoder left, right;
    private double ticksPerInch;

    private Server myServer;

    private double oldSec;
    private double oldDistance;
    private double oldHeading;
    private Vector2d position;

    public NavRobot(AHRS navx, Encoder left, Encoder right, double ticksPerInch) throws IOException {
        this.navx = navx;
        this.left = left;
        this.right = right;
        this.ticksPerInch = ticksPerInch;

        reset();
        myServer = new Server();
        myServer.start();
    }

    public void position() throws IOException {
        double pastSec = System.currentTimeMillis()/1000.0-oldSec;
        if(pastSec > 0.25) {
            double distance = ((left.getRaw() +
                    right.getRaw())/2)*ticksPerInch;

            double diffDistance = distance - oldDistance;
            double heading = navx.getYaw() - oldHeading;
            Vector2d change = new Vector2d(diffDistance, heading);
            position = position.add(change);
            myServer.addData(position.x,position.y);

            oldSec = System.currentTimeMillis()/1000.0;
            oldDistance = distance;
            oldHeading = heading;
        }

    }

    public void reset(){
        oldSec = 0;
        oldDistance = 0;
        oldHeading = 0;
        position = new Vector2d(0,0);
    }

}
