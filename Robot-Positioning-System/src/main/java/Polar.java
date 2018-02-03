public class Polar implements Vector {
    private double magnitude;
    private Angle angle;

    public Polar(double magnitude, Angle angle) {
        this.magnitude = magnitude;
        this.angle = angle;
    }
    
    @Override
    public double getX() {
        return magnitude * angle.cos();
    }

    @Override
    public void setX(double x) {
        magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(getY(), 2));
        angle = new Angle(Angle.AngleUnit.RADIAN, Math.atan2(getY(), x));
    }
    
    @Override
    public double getY() {
        return magnitude * angle.sin();
    }
    
    @Override
    public void setY(double y) {
        magnitude = Math.sqrt(Math.pow(getX(), 2) + Math.pow(y, 2));
        angle = new Angle(Angle.AngleUnit.RADIAN, 
                Math.atan2(y, getX()));
    }
    
    @Override
    public double getManitude() {
        return magnitude;
    }
    
    @Override
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }
    
    @Override
    public Angle getAngle() {
        return angle;
    }

    @Override
    public void setAngle(Angle angle) {
        this.angle = angle;
    }
    
    @Override
    public Vector add(Vector vector) {
        return new XY(this.getX() + vector.getX(), this.getY() + vector.getY());
    }

    @Override
    public Vector subtract(Vector vector) {
        return add(vector.scale(-1));
    }

    @Override
    public Vector scale(double scalar) {
        return new Polar(magnitude * scalar, angle);
    }

    @Override
    public double cross(Vector vector) {
        return this.getManitude() * vector.getManitude() * angleBetween(vector).sin();
    }

    @Override
    public double dot(Vector vector) {
        return this.getX() * vector.getX() + this.getY() * vector.getY();
    }

    @Override
    public Vector rotate(Angle angle) {
        return new Polar(magnitude, new Angle(Angle.AngleUnit.DEGREE,
                this.angle.getDegrees() + angle.getDegrees()));
    }
    
    @Override
    public Angle angleBetween(Vector vector) {
        return new Angle(Angle.AngleUnit.DEGREE,
                Math.abs(this.angle.getDegrees() - angle.getDegrees()));
    }
    
    @Override
    public Vector normalize() {
        return new Polar(1, angle);
    }
    
    @Override
    public Vector copy() {
        return new Polar(magnitude, angle);
    }
}