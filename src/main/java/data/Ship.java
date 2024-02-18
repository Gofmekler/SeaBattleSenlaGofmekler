package data;

import java.util.Objects;

public class Ship {

    private final String shipType;
    private final int shipDecks;
    private int shipHP;
    private boolean isDestroyed;
    private String direction;
    private int xCoordinate;
    private int yCoordinate;

    public Ship(String shipType, int shipDecks) {
        this.shipType = shipType;
        this.shipDecks = shipDecks;
        this.shipHP = shipDecks;
        this.isDestroyed = false;
    }

    public int getShipCoordinateX() {
        return xCoordinate;
    }

    public void setShipCoordinateX(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getShipCoordinateY() {
        return yCoordinate;
    }

    public void setShipCoordinateY(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getShipDecks() {
        return shipDecks;
    }

    public String getShipType() {
        return shipType;
    }

    public boolean isDestroyed() {
        return this.isDestroyed;
    }


    public void hitShip() {
        if (this.shipHP > 0) {
            this.shipHP -= 1;
            updateShipCondition();
        }

    }

    public void updateShipCondition() {
        if (this.shipHP == 0) {
            this.isDestroyed = true;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return shipDecks == ship.shipDecks &&
                xCoordinate == ship.xCoordinate &&
                yCoordinate == ship.yCoordinate &&
                Objects.equals(direction, ship.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipDecks, direction, xCoordinate, yCoordinate);
    }
}
