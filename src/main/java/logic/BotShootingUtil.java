package logic;

import data.Cell;
import data.Configuration;
import data.Player;
import data.Ship;
import data.enums.CellStatus;
import data.enums.SystemMessages;
import utils.RandomNumberGenerator;

import java.util.HashSet;
import java.util.Set;

public class BotShootingUtil extends PlayerShootingUtil {

    private final int STEP = 2;
    private boolean isPointFixed;
    private String shotDirection;
    private int shotCoordX;
    private int shotCoordY;
    private int offset;
    private final Set<String> firingDirections = new HashSet<>();

    public BotShootingUtil(Player enemy) {
        super(enemy);
        this.isPointFixed = false;
        this.offset = 0;
    }

    @Override
    public boolean shot(int[] shotCoordinates) {
        if (isPointFixed) {
            Cell fireCell = getEnemyPlayer().fieldOperations()
                    .getCellByDirection(shotDirection, shotCoordX, shotCoordY, offset + 1);
            Ship enemyShip = fireCell.getCellShip();
            if (enemyShip != null) {
                enemyShip.hitShip();
                if (enemyShip.isDestroyed()) {
                    System.out.println(SystemMessages.botKill.getMessage());
                    processDestroyedShip(enemyShip);
                    clearShotCoordinates();
                } else {
                    System.out.println(SystemMessages.botHit.getMessage());
                    fireCell.setCellStatus(CellStatus.HIT.getStatus());
                    getEnemyBannedCells().add(fireCell);
                    offset++;
                }
                return true;
            } else {
                System.out.println(SystemMessages.botMiss.getMessage());
                fireCell.setCellStatus(CellStatus.MISSED.getStatus());
                getEnemyBannedCells().add(fireCell);
                this.shotDirection = generateFireDirection(new int[]{shotCoordX, shotCoordY});
                this.offset = 1;
                return false;
            }
        } else {
            return randomShooting(shotCoordinates);
        }
    }

    public boolean randomShooting(int[] shotCoordinates) {
        int[] validShotCoordinates = checkOrGenerateCoordinates(shotCoordinates);
        Cell fireCell = getEnemyPlayer().fieldOperations()
                .getCellByCoords(validShotCoordinates[0], validShotCoordinates[1]);
        Ship enemyShip = fireCell.getCellShip();
        if (enemyShip != null) {
            enemyShip.hitShip();
            if (enemyShip.isDestroyed()) {
                System.out.println(SystemMessages.botKill.getMessage());
                processDestroyedShip(enemyShip);
            } else {
                System.out.println(SystemMessages.botHit.getMessage());
                fireCell.setCellStatus(CellStatus.HIT.getStatus());
                getEnemyBannedCells().add(fireCell);
                setShotCoordinates(validShotCoordinates);
            }
            return true;
        } else {
            System.out.println(SystemMessages.botMiss.getMessage());
            fireCell.setCellStatus(CellStatus.MISSED.getStatus());
            getEnemyBannedCells().add(fireCell);
            return false;
        }
    }


    private void setShotCoordinates(int[] validShotCoordinates) {
        this.isPointFixed = true;
        this.shotCoordX = validShotCoordinates[0];
        this.shotCoordY = validShotCoordinates[1];
        this.shotDirection = generateFireDirection(validShotCoordinates);
        this.offset = 1;
    }

    private void clearShotCoordinates() {
        this.isPointFixed = false;
    }

    private int[] checkOrGenerateCoordinates(int[] shotCoordinates) {
        Cell testCell = getEnemyPlayer().fieldOperations().getCellByCoords(shotCoordinates[0], shotCoordinates[1]);
        if (!getEnemyBannedCells().contains(testCell)) {
            return shotCoordinates;
        } else {
            return generateCoordinates();
        }
    }

    private int[] generateCoordinates() {
        boolean areCoordsValid = false;
        int[] newCoords = new int[2];
        while (!areCoordsValid) {
            newCoords[0] = RandomNumberGenerator.generateRandomInRange(Configuration.getFieldSize());
            newCoords[1] = RandomNumberGenerator.generateRandomInRange(Configuration.getFieldSize());
            Cell testCell = getEnemyPlayer().fieldOperations().getCellByCoords(newCoords[0], newCoords[1]);
            if (!getEnemyBannedCells().contains(testCell)) {
                areCoordsValid = true;
            }
        }
        return newCoords;
    }

    private String generateFireDirection(int[] coordinates) {
        boolean isCorrectDirection = false;
        String direction = "";
        while (!isCorrectDirection) {
            direction = RandomNumberGenerator.generateRandomDirection();
            if (!firingDirections.contains(direction)) {
                if (getEnemyPlayer().fieldOperations().checkFieldBorder(direction, coordinates[0],
                        coordinates[1], STEP)) ;
                {
                    if (checkNextCell(direction, coordinates[0], coordinates[1], STEP)) {
                        break;
                    }
                }
            }
        }
        return direction;
    }

    private boolean checkNextCell(String direction, int xCoord, int yCoord, int step) {
        Cell checkCell = getEnemyPlayer().fieldOperations().getCellByDirection(direction, xCoord, yCoord, step);

        if (checkCell == null) {
            return false;
        } else {
            return !getEnemyBannedCells().contains(checkCell);
        }
    }
}
