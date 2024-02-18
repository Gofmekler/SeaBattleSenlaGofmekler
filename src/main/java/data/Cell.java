package data;

import java.util.Objects;

public class Cell {

    private final int cellIndex;
    private final int xCoordinate;
    private final int yCoordinate;
    private String cellStatus;
    private Ship cellShip;
    private boolean isInitializationCell = false;

    public Cell(int cellIndex, int xCoordinate, int yCoordinate, String cellStatus) {
        this.cellIndex = cellIndex;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.cellStatus = cellStatus;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public String getCellStatus() {
        return cellStatus;
    }

    public void setCellStatus(String cellStatus) {
        this.cellStatus = cellStatus;
    }

    public boolean isShipInCell() {
        return cellShip != null;
    }

    public void removeShip() {
        this.cellShip = null;
    }

    public void setCellShip(Ship cellShip) {
        this.cellShip = cellShip;
    }

    public Ship getCellShip() {
        return cellShip;
    }

    public void setInitializationCell() {
        this.isInitializationCell = true;
    }

    public void cleatInitializationStatus() {
        this.isInitializationCell = false;
    }

    public boolean isInitializationCell() {
        return isInitializationCell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return cellIndex == cell.cellIndex &&
                xCoordinate == cell.xCoordinate &&
                yCoordinate == cell.yCoordinate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellIndex, xCoordinate, yCoordinate);
    }
}

