import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Encoder;
import utils.Checkpoint;
import utils.Vector2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class NavigationSystem {

    public enum DriveTrain{
        TANK, MECANUM
    }

    private AHRS navx;

    private DriveTrain driveTrain;

    // Tank Encoders
    private Encoder left, right;

    // Mecanum Encoders
    private Encoder frontLeft, frontRight, backLeft, backRight;

    private ArrayList<Checkpoint> checkpoints = new ArrayList<>();

    private double inchPerTick;
    private double oldSec;
    private double oldDistance;
    private double oldHeading;
    private Vector2d position;

    public NavigationSystem addNavX(AHRS navx){
        this.navx = navx;
        return this;
    }

    public NavigationSystem addTankEncoders(Encoder left, Encoder right, double ticksPerInch){
        this.left = left;
        this.right = right;
        inchPerTick = 1/ticksPerInch;
        driveTrain = DriveTrain.TANK;
        return this;
    }

    public NavigationSystem addMecanumEncoders(Encoder frontLeft, Encoder frontRight, Encoder backLeft, Encoder backRight, double ticksPerInch){
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
        inchPerTick = 1/ticksPerInch;
        driveTrain = DriveTrain.MECANUM;
        return this;
    }

    /**
     * Adds known checkpoints for on the fly position recalibration
     * @param checkpoints
     * @return
     */
    public NavigationSystem addCheckpoint(Checkpoint... checkpoints){
        for(Checkpoint checkpoint:checkpoints){
            this.checkpoints.add(checkpoint);
        }
        return this;
    }

    public void updatePosition() throws IOException {
        double pastSec = System.currentTimeMillis()/1000.0-oldSec;
        if(pastSec > 0.25) {
            switch (driveTrain) {
                case TANK:
                    double distance = ((left.get() * inchPerTick + right.get() * inchPerTick) / 2);
                    double angle = navx.getYaw() - oldHeading;
                    double diffDistance = distance - oldDistance;
                    Vector2d change = new Vector2d(diffDistance * Math.sin(angle * Math.PI / 180),
                            diffDistance * Math.cos(angle * Math.PI / 180));
                    position = position.add(change);

                    oldSec = System.currentTimeMillis() / 1000.0;
                    oldDistance = distance;
                    oldHeading = angle;
                    break;

                case MECANUM:
                    //TODO
                    break;
            }

            SmartDashboard.putNumber("RPS X", position.getX());
            SmartDashboard.putNumber("RPS Y", position.getY());
        }
    }

    /**
     * Gets Current Position
     * @return Vector2d position
     */
    public Vector2d getPosition(){
        return position;
    }

    /**
     * Resets current position to position of known checkpoint
     * @param checkpoint Known position
     * @param reset If reset should occur
     */
    public void resetPosition(Checkpoint checkpoint, boolean reset){
        if(reset){
            position = checkpoint.getPosition();
        }
    }

}
