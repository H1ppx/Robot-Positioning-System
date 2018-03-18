package Robot;

import socket.Rio;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Encoder;
import vector.Vector2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.IOException;

public class NavRobot {

    private final AHRS navx;
    private final Encoder left, right;
    private final double inchPerTick;

    private Rio rio;

    private double oldSec;
    private double oldDistance;
    private double oldHeading;
    private Vector2d position;

    public NavRobot(AHRS navx, Encoder left, Encoder right, double ticksPerInch) {
        this.navx = navx;
        this.left = left;
        this.right = right;
        this.inchPerTick = 1/ticksPerInch;
}

    public void position() throws IOException {
        double pastSec = System.currentTimeMillis()/1000.0-oldSec;
        if(pastSec > 0.25) {
            double distance = ((left.get()*inchPerTick +
                    right.get()*inchPerTick)/2);

            double angle = navx.getYaw() - oldHeading;
            double diffDistance = distance - oldDistance;
            SmartDashboard.putNumber("Heading", angle);
            Vector2d change = new Vector2d(diffDistance*Math.sin(angle*Math.PI/180),
                    diffDistance*Math.cos(angle*Math.PI/180));
            position = position.add(change);
            setPositionBounds();
            rio.send(position);

            oldSec = System.currentTimeMillis()/1000.0;
            oldDistance = distance;
            oldHeading = angle;
        }

    }

    public void startServer(){
        rio = new Rio();
        rio.start();
    }

    public void resetLeft(){
        oldSec = 0;
        oldDistance = 0;
        oldHeading = 0;
        position = new Vector2d(323/3,0);
    }

    public void resetMiddle(){
        oldSec = 0;
        oldDistance = 0;
        oldHeading = 0;
        position = new Vector2d(323/2,0);
    }

    public void resetRight(){
        oldSec = 0;
        oldDistance = 0;
        oldHeading = 0;
        position = new Vector2d(323*2/3,0);
    }

    private void setPositionBounds() {
        if(position.x < 0){
            position.x = 0;
        }

        if(position.x > 323){
            position.x = 323;
        }

        if(position.y < 0){
            position.y = 0;
        }

        if(position.y > 648){
            position.y = 648;
        }
    }

}
