package data.enums;

public enum Directions {
    UP("up"),
    RIGHT("right"),
    DOWN("down"),
    LEFT("left");

    private final String direction;

    Directions(String value) {
        this.direction = value;
    }

    public String getDirection() {
        return direction;
    }
}
