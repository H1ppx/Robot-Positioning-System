public class XY  implements Vector {
    private double x;
    private double y;

    public XY(double x, double y){
        this.x = x;
        this.y = y;
    }

    public XY(XY vector){
        x = vector.getY();
        y = vector.getY();
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getManitude() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    @Override
    public void setMagnitude(double magnitude) {
        this.x = getAngle().cos() * magnitude;
        this.y = getAngle().sin() * magnitude;
    }

    @Override
    public Angle getAngle() {
        return new Angle(Angle.AngleUnit.RADIAN, Math.atan2(y, x));
    }

    @Override
    public void setAngle(Angle angle) {
        this.x = getManitude() * angle.cos();
        this.y = getManitude() * angle.sin();
    }

    @Override
    public Vector add(Vector vector) {
        return new XY(x + vector.getX(), y + vector.getY());
    }

    @Override
    public Vector subtract(Vector vector) {
        return add(vector.scale(-1));
    }

    @Override
    public Vector scale(double scalar) {
        return new XY(x * scalar, y * scalar);
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
        return new Polar(getManitude(), new Angle(Angle.AngleUnit.DEGREE,
                getAngle().getDegrees() + angle.getDegrees()));
    }

    @Override
    public Angle angleBetween(Vector vector) {
        return new Angle(Angle.AngleUnit.DEGREE,
                Math.abs(vector.getAngle().getDegrees() - getAngle().getDegrees()));
    }

    @Override
    public Vector normalize() {
        return new XY(x/getManitude(), y/getManitude());
    }

    @Override
    public Vector copy() {
        return new XY(x, y);
    }
}