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

    public void updatePosition() throws IOException {
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

            SmartDashboard.putNumber("RPS X", position.getX());
            SmartDashboard.putNumber("RPS Y", position.getY());

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
        position = new Vector2d(323/4,0);
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
        position = new Vector2d(323*3/4,0);
    }

    public Vector2d getPositon(){
        return position;
    }

    private void setPositionBounds(){
        if(position.getX() < 0){
            position.setX(0);
        }

        if(position.getX() > 323){
            position.setX(323);
        }

        if(position.getY() < 0){
            position.setY(0);
        }

        if(position.getY() > 648){
            position.setY(648);
        }
    }

}
