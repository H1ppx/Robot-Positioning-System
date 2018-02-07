package Socket;

/**
 * @author William Chu
 */

public class XY {

    private double x,y;

    /**
     * @param  x x coordinate of a 2D point represented as a double
     * @param  y y coordinate of a 2D point represented as a double
     */

    public XY(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * @return x coordinate of a 2D point represented as a double
     */

    public double getX(){
        return this.x;
    }

    /**
     * @return y coordinate of a 2D point represented as a double
     */

    public  double getY(){
        return this.y;
    }

}
