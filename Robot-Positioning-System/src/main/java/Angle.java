public class Angle {


    enum AngleUnit{
        RADIAN, DEGREE
    }

    private double degrees;

    public final Angle ZERO = new Angle(AngleUnit.DEGREE, 0);

    public Angle(AngleUnit AngleUnit, double angle){
        if(AngleUnit == AngleUnit.DEGREE){
            degrees = angle;
        }else{
            degrees = angle * Math.PI/180;
        }
    }

    public double getDegrees(){
        return degrees;
    }

    public double getRadians(){
        return degrees * (Math.PI / 180);
    }

    public void setDegrees(double degrees){
        this.degrees = degrees;
    }

    public void setRadians(double radians){
        this.degrees = radians * (180/Math.PI);
    }

    public double sin(){
        return Math.sin(getRadians());
    }

    public double cos(){
        return Math.cos(getRadians());
    }

    public double tan(){
        return Math.tan(getRadians());
    }
}