package logic;

import data.Cell;
import data.Ship;
import data.enums.CellStatus;
import utils.RandomNumberGenerator;

import java.util.List;

public class AutomaticShipPlacer {

    private final FieldOperations fieldOperations;

    public AutomaticShipPlacer(FieldOperations fieldOperationsObject) {
        this.fieldOperations = fieldOperationsObject;
    }

    public void placeShips(List<Ship> ships) {
        for (Ship ship : ships) {
            placeShip(ship);
        }
    }

    public void placeShip(Ship ship) {
        boolean isShipPlaced = false;
        while (!isShipPlaced) {

            int randomCoordinateX = RandomNumberGenerator.generateRandomInRange(16);
            int randomCoordinateY = RandomNumberGenerator.generateRandomInRange(16);
            String randomDirection = RandomNumberGenerator.generateRandomDirection();
            Cell selectedCell = fieldOperations.getCellByCoords(randomCoordinateX, randomCoordinateY);
            if (selectedCell != null && selectedCell.getCellStatus().equals(CellStatus.HIDDEN.getStatus())) {
                boolean isInsideBorders = fieldOperations.checkFieldBorder(randomDirection, randomCoordinateX,
                        randomCoordinateY, ship.getShipDecks());
                if (isInsideBorders) {
                    boolean isRadiusFree = checkRadius(randomDirection, randomCoordinateX, randomCoordinateY,
                            ship.getShipDecks());
                    if (isRadiusFree) {
                        isShipPlaced = true;
                        initializeShip(ship, randomDirection, randomCoordinateX, randomCoordinateY);
                    }
                }
            }
        }
    }

    public boolean checkRadius(String direction, int xCoord, int yCoord, int shipDecks) {
        switch (direction) {
            case "up": {
                List<Cell> radiusCellsList = fieldOperations.getTopDirectionRadius(xCoord, yCoord, shipDecks);
                return isRadiusCorrect(radiusCellsList);
            }
            case "right": {
                List<Cell> radiusCellsList = fieldOperations.getRightDirectionRadius(xCoord, yCoord, shipDecks);
                return isRadiusCorrect(radiusCellsList);
            }
            case "down": {
                List<Cell> radiusCellsList = fieldOperations.getBottomDirectionRadius(xCoord, yCoord, shipDecks);
                return isRadiusCorrect(radiusCellsList);
            }
            default: {
                List<Cell> radiusCellsList = fieldOperations.getLeftDirectionRadius(xCoord, yCoord, shipDecks);
                return isRadiusCorrect(radiusCellsList);
            }
        }
    }

    private boolean isRadiusCorrect(List<Cell> cellsInRadius) {
        for (Cell cell : cellsInRadius) {
            if (cell.isShipInCell()) {
                return false;
            }
        }
        return true;
    }

    private void initializeShip(Ship ship, String randomDirection, int randomCoordX, int randomCoordY) {
        ship.setDirection(randomDirection);
        ship.setShipCoordinateX(randomCoordX);
        ship.setShipCoordinateY(randomCoordY);
        List<Cell> shipCells = fieldOperations.getShipCells(ship);
        for (Cell cell : shipCells) {
            cell.setCellShip(ship);
        }
    }
}
