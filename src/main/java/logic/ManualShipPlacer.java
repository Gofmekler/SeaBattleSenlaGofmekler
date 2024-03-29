package logic;

import data.Cell;
import data.Ship;
import data.enums.Directions;
import data.enums.SystemMessages;

import java.util.List;
import java.util.Scanner;

public class ManualShipPlacer {

    private final FieldPrinter fieldPrinter;
    private final FieldOperations fieldOperations;
    private final InputOperator inputOperator;
    private static boolean isInputExitCondition = false;

    public ManualShipPlacer(FieldOperations fieldOperationsObject) {
        this.fieldOperations = fieldOperationsObject;
        this.fieldPrinter = new FieldPrinter();
        this.inputOperator = new InputOperator();
    }

    public static void setInputExitCondition() {
        isInputExitCondition = true;
    }

    public void placeShips(List<Ship> ships, Scanner scanner) {
        System.out.println("Place ships manually");
        for (Ship ship : ships) {
            if (ship.getShipDecks() > 1) {
                placeShip(ship, scanner);
            } else {
                placeSingleShip(ship, scanner);
            }
            if (isInputExitCondition) {
                break;
            }
        }
        if (isInputExitCondition) {
            Application.setApplicationExitCondition();
        }
    }

    public boolean checkPotentialDirection(Ship ship, String direction) {
        if (fieldOperations.checkFieldBorder(direction, ship.getShipCoordinateX(),
                ship.getShipCoordinateY(), ship.getShipDecks())) {
            ship.setDirection(direction);
            List<Cell> radiusList = fieldOperations.getShipAndRadiusCells(ship);
            for (Cell c : radiusList) {
                if (c.getCellShip() != null) {
                    System.out.println(SystemMessages.errorAnotherShipHere.getMessage());
                    return false;
                }
            }
            return true;
        } else {
            System.out.println(SystemMessages.errorCrossBorder.getMessage());
            return false;
        }
    }

    public boolean checkEnteredCoordinates(Ship ship, int[] indexes) {
        Cell potentialPlacementCell = fieldOperations.getCellByCoords(indexes[0], indexes[1]);
        if (potentialPlacementCell.getCellShip() == null) {
            pinPotentialCellAndShip(ship, potentialPlacementCell);
            if (fieldOperations.getPotentialDirectionsNumber(ship, potentialPlacementCell) > 0) {
                return true;
            }
        }
        System.out.println(SystemMessages.errorCantPlaceHere.getMessage());
        unPinPotentialCellAndShip(ship, potentialPlacementCell);
        return false;
    }

    public void placeSingleShip(Ship ship, Scanner scanner) {
        System.out.println("Placing " + ship.getShipType() + " ship");
        boolean isCellFixed = false;
        while (!isCellFixed && !isInputExitCondition) {
            fieldPrinter.printSingleField(fieldOperations.getField());
            int[] indexes = inputOperator.enterIndexes(scanner, fieldOperations, ship.getShipType());
            if (!isInputExitCondition) {
                Cell potentialPlacementCell = fieldOperations.getCellByCoords(indexes[0], indexes[1]);
                if (potentialPlacementCell.getCellShip() == null) {
                    pinPotentialCellAndShip(ship, potentialPlacementCell);
                    if (fieldOperations.getPotentialDirectionsNumber(ship, potentialPlacementCell) > 0) {
                        if (checkSingleShip(ship) == 0) {
                            ship.setDirection(Directions.UP.getDirection());
                            initializeShip(ship);
                            break;
                        }
                    }
                }
                System.out.println(SystemMessages.errorCantPlaceHere.getMessage());
                unPinPotentialCellAndShip(ship, potentialPlacementCell);
            }
        }
    }

    public void placeShip(Ship ship, Scanner scanner) {
        System.out.println("Placing " + ship.getShipType() + " ship");
        boolean isCellFixed = false;
        while (!isCellFixed && !isInputExitCondition) {
            fieldPrinter.printSingleField(fieldOperations.getField());
            int[] indexes = inputOperator.enterIndexes(scanner, fieldOperations, ship.getShipType());
            if (!isInputExitCondition) {
                if (checkEnteredCoordinates(ship, indexes)) break;
            }
        }
        if (!isInputExitCondition) {
            processPlacementDirection(ship, scanner);
        }
    }

    private void processPlacementDirection(Ship ship, Scanner scanner) {
        boolean isDirectionEntered = false;
        while (!isDirectionEntered) {
            fieldPrinter.printSingleField(fieldOperations.getField());
            String direction = inputOperator.enterDirection(scanner, ship.getShipType());
            if (checkPotentialDirection(ship, direction)) {
                initializeShip(ship);
                break;
            }
        }
    }

    private void pinPotentialCellAndShip(Ship ship, Cell cell) {
        cell.setInitializationCell();
        ship.setShipCoordinateX(cell.getxCoordinate());
        ship.setShipCoordinateY(cell.getyCoordinate());
    }

    private void unPinPotentialCellAndShip(Ship ship, Cell cell) {
        cell.cleatInitializationStatus();
        ship.setShipCoordinateX(1);
        ship.setShipCoordinateY(1);
    }

    private void initializeShip(Ship ship) {
        Cell placementCell = fieldOperations.getCellByCoords(ship.getShipCoordinateX(), ship.getShipCoordinateY());
        placementCell.cleatInitializationStatus();
        List<Cell> shipCells = fieldOperations.getShipCells(ship);
        for (Cell cell : shipCells) {
            cell.setCellShip(ship);
        }
    }

    private long checkSingleShip(Ship ship) {
        return fieldOperations.getRightDirectionRadius(ship.getShipCoordinateX(), ship.getShipCoordinateY(), ship.getShipDecks())
                .stream().filter(c -> c.getCellShip() != null).count();
    }
}
