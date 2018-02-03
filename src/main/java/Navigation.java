import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Navigation {


    Server myServer;

    Vector position;

    double oldDistance, distance;
    double heading, oldHeading;
    double seconds, oldSeconds;

    public void init(){
        myServer = new Server();
        myServer.start();
        oldDistance = 0;
        distance = 0;
        oldSeconds = System.currentTimeMillis()/1000.0;
        position = new Polar(0,new Angle(Angle.AngleUnit.DEGREE,0));
    }

    public void run(AHRS navx, Encoder left, Encoder right, double ticksPerInch, double pauseTime, boolean debugs){
        seconds = System.currentTimeMillis()/1000.0-oldSeconds;
        if(seconds > pauseTime) {
            distance = ((left.getRaw()*ticksPerInch + right.getRaw()*ticksPerInch)/ 2);
            heading = navx.getYaw();
            double differenceDistance = distance - oldDistance;
            double differenceHeading = heading - oldHeading;
            
            Vector positionChange = new Polar(differenceDistance,
                    new Angle(Angle.AngleUnit.DEGREE, differenceHeading));
            position = position.add(positionChange);

            myServer.xOut = position.getX();
            myServer.yOut = position.getY();

            if(debugs){
                SmartDashboard.putNumber("Output X", myServer.xOut);
                SmartDashboard.putNumber("Output Y", myServer.yOut);
            }

            oldDistance = distance;
            oldSeconds = System.currentTimeMillis()/1000.0;
        }
    }

}
