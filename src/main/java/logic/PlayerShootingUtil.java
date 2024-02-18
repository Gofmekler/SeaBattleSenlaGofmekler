package logic;

import data.Cell;
import data.Player;
import data.Ship;
import data.enums.CellStatus;
import data.enums.SystemMessages;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerShootingUtil {

    private final Set<Cell> enemyBannedCells = new HashSet<>();
    private final Player enemyPlayer;

    public PlayerShootingUtil(Player enemy) {
        this.enemyPlayer = enemy;
    }

    public Set<Cell> getEnemyBannedCells() {
        return enemyBannedCells;
    }

    public Player getEnemyPlayer() {
        return enemyPlayer;
    }

    public boolean isCellShot(Cell potentialFireCell) {
        return enemyBannedCells.contains(potentialFireCell);
    }

    public boolean shot(int[] shotCoordinates) {
        Cell fireCell = enemyPlayer.fieldOperations().getCellByCoords(shotCoordinates[0], shotCoordinates[1]);
        Ship enemyShip = fireCell.getCellShip();
        if (enemyShip != null) {
            enemyShip.hitShip();
            if (enemyShip.isDestroyed()) {
                System.out.println(SystemMessages.playerKill.getMessage() + " " + enemyShip.getShipType());
                processDestroyedShip(enemyShip);
            } else {
                System.out.println(SystemMessages.playerHit.getMessage());
                fireCell.setCellStatus(CellStatus.HIT.getStatus());
                enemyBannedCells.add(fireCell);
            }
            return true;
        } else {
            System.out.println(SystemMessages.playerMiss.getMessage());
            fireCell.setCellStatus(CellStatus.MISSED.getStatus());
            enemyBannedCells.add(fireCell);
            return false;
        }
    }

    public void processDestroyedShip(Ship destroyedShip) {
        List<Cell> destroyedShipCells = enemyPlayer.fieldOperations().getShipAndRadiusCells(destroyedShip);
        for (Cell cell : destroyedShipCells) {
            cell.setCellStatus(CellStatus.HIT.getStatus());
            enemyBannedCells.add(cell);
        }
        removeShipFromCells(destroyedShip);
        enemyPlayer.shipsOperations().removeShip(destroyedShip);
    }

    private void removeShipFromCells(Ship destroyedShip) {
        for (Cell cell : enemyPlayer.fieldOperations().getShipCells(destroyedShip)) {
            if (cell.getCellShip().equals(destroyedShip)) {
                cell.removeShip();
            }
        }
    }
}
