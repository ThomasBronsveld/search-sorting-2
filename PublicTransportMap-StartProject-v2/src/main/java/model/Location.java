package model;

public class Location {

    final double SQUARE_TRAVEL_TIME = 1.5;
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double travelTime(Location location) {
        return (Math.abs(getX() - location.getX()) + Math.abs(getY()) - location.getY()) * SQUARE_TRAVEL_TIME;
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }
}
