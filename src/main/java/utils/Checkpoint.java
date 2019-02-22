package utils;

public class Checkpoint {

    private final String name;
    private final Vector2d position;

    public Checkpoint(String name, Vector2d position){
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public Vector2d getPosition() {
        return position;
    }
}
