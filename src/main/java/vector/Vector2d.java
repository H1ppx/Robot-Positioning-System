package vector;

public class Vector2d {

    public double x, y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d add(Vector2d vector2d){
        return new Vector2d(this.x+vector2d.x,this.y+vector2d.y);
    }
}
