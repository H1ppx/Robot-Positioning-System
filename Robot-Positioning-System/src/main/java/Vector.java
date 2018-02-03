public interface Vector {

    public double getX();

    public void setX(double x);

    public double getY();

    public void setY(double y);

    public double getManitude();

    public void setMagnitude(double magnitude);

    public Angle getAngle();

    public void setAngle(Angle angle);

    public Vector add(Vector vector);

    public Vector subtract(Vector vector);

    public Vector scale(double scalar);

    public double cross(Vector vector);

    public double dot(Vector vector);

    public Vector rotate(Angle angle);

    public Angle angleBetween(Vector vector);

    public Vector normalize();

    public Vector copy();

}