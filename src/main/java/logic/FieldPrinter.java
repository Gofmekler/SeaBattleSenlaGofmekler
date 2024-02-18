package logic;

import data.Cell;
import data.Configuration;
import data.enums.CellStatus;
import data.enums.ConsoleColors;

import java.util.List;
import java.util.stream.Collectors;

public class FieldPrinter {

    private final int fieldSize = Configuration.getFieldSize();

    public void printDoubleField(List<Cell> playerField, List<Cell> botField) {
        printDoubleFieldHead();

        for (int i = 1; i <= fieldSize; i++) {
            printDoubleFieldLine(playerField, botField, i);
        }
        System.out.println();
    }

    public void printSingleField(List<Cell> playerField) {
        printSingleFieldHead();
        for (int i = 1; i <= fieldSize; i++) {
            printSingleFieldLine(playerField, i);
        }
        System.out.println();
    }

    private void printSingleFieldHead() {
        System.out.println();
        for (int i = 0; i < fieldSize; i++) {
            if (i == 0) {
                System.out.print("   ");
            }
            System.out.print("  " + ConsoleColors.GREEN_BOLD.color() +
                    (char) (65 + i) + ConsoleColors.RESET.color());
        }
        System.out.println();
    }

    private void printDoubleFieldHead() {
        System.out.println();
        System.out.println("\t   Players field \t\t\t   Computer field\n");
        for (int i = 0; i < fieldSize; i++) {
            if (i == 0) {
                System.out.print("   ");
            }
            System.out.print("  " + ConsoleColors.GREEN_BOLD.color() +
                    (char) (65 + i) + ConsoleColors.RESET.color());
        }
        System.out.print("\t");
        for (int i = 0; i < fieldSize; i++) {
            if (i == 0) {
                System.out.print("   ");
            }
            System.out.print("  " + ConsoleColors.GREEN_BOLD.color() +
                    (char) (65 + i) + ConsoleColors.RESET.color());
        }
        System.out.println();
    }

    private void printSingleFieldLine(List<Cell> playerField, int yCoord) {
        System.out.print(ConsoleColors.GREEN_BOLD.color() + yCoord + ConsoleColors.RESET.color() + "\t");
        printHumanFieldInitialization(playerField, yCoord);
        System.out.println();
    }

    private void printDoubleFieldLine(List<Cell> playerField, List<Cell> botField, int yCoord) {
        String indent = "   ";
        if (yCoord == 10) {
            indent = "  ";
        }
        System.out.print(ConsoleColors.GREEN_BOLD.color() + yCoord + ConsoleColors.RESET.color() + indent);
        printHumanFieldComponents(playerField, yCoord);
        System.out.print("\t");
        System.out.print(ConsoleColors.GREEN_BOLD.color() + yCoord + ConsoleColors.RESET.color() + indent);
        printBotFieldComponents(botField, yCoord);
        System.out.println();
    }

    private void printHumanFieldComponents(List<Cell> printingField, int yCoord) {
        List<Cell> filteredList =
                printingField.stream().filter(cell -> cell.getyCoordinate() == yCoord).collect(Collectors.toList());

        for (Cell c : filteredList) {
            if (c.getCellShip() != null && c.getCellStatus().equals(CellStatus.HIT.getStatus())) {
                System.out.print(ConsoleColors.RED.color() + " X " + ConsoleColors.RESET.color());
            } else if (c.getCellShip() != null) {
                System.out.print(" □ ");
            } else if (c.getCellStatus().equals(CellStatus.HIDDEN.getStatus())) {
                System.out.print(" . ");
            } else if (c.getCellStatus().equals(CellStatus.HIT.getStatus())) {
                System.out.print(ConsoleColors.RED.color() + " X " + ConsoleColors.RESET.color());
            } else if (c.getCellStatus().equals(CellStatus.MISSED.getStatus())) {
                System.out.print(ConsoleColors.BLUE.color() + " O " + ConsoleColors.RESET.color());
            }
        }
    }

    private void printHumanFieldInitialization(List<Cell> printingField, int yCoord) {
        List<Cell> filteredList =
                printingField.stream().filter(cell -> cell.getyCoordinate() == yCoord).collect(Collectors.toList());

        for (Cell c : filteredList) {
            if (c.isInitializationCell()) {
                System.out.print(ConsoleColors.GREEN.color() + " □ " + ConsoleColors.RESET.color());
            } else if (c.getCellShip() != null) {
                System.out.print(" □ ");
            } else if (c.getCellStatus().equals(CellStatus.HIDDEN.getStatus())) {
                System.out.print(" . ");
            }
        }
    }

    private void printBotFieldComponents(List<Cell> printingField, int yCoord) {
        List<Cell> filteredList =
                printingField.stream().filter(cell -> cell.getyCoordinate() == yCoord).collect(Collectors.toList());

        for (Cell c : filteredList) {
            if (c.getCellStatus().equals(CellStatus.HIDDEN.getStatus())) {
                System.out.print(" . ");
            } else if (c.getCellStatus().equals(CellStatus.HIT.getStatus())) {
                System.out.print(ConsoleColors.RED.color() + " X " + ConsoleColors.RESET.color());
            } else if (c.getCellStatus().equals(CellStatus.MISSED.getStatus())) {
                System.out.print(ConsoleColors.BLUE.color() + " O " + ConsoleColors.RESET.color());
            }
        }
    }
}
