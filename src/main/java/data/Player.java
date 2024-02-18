package data;

import logic.AutomaticShipPlacer;
import logic.FieldOperations;
import logic.ManualShipPlacer;
import logic.ShipOperations;

import java.util.List;

public class Player {

    private final FieldOperations battleFieldOperations = new FieldOperations();
    private final ShipOperations shipOperations = new ShipOperations();
    private final AutomaticShipPlacer automaticShipPlacer;
    private final ManualShipPlacer manualShipPlacer;
    private String NickName;

    public Player(String nickName) {
        this.NickName = nickName;
        battleFieldOperations.generateField();
        shipOperations.generateShips();
        automaticShipPlacer = new AutomaticShipPlacer(battleFieldOperations);
        manualShipPlacer = new ManualShipPlacer(battleFieldOperations);
    }

    public Player() {
        battleFieldOperations.generateField();
        shipOperations.generateShips();
        automaticShipPlacer = new AutomaticShipPlacer(battleFieldOperations);
        manualShipPlacer = new ManualShipPlacer(battleFieldOperations);
    }

    public String getNickName() {
        return NickName;
    }

    public List<Cell> getField() {
        return battleFieldOperations.getField();
    }

    public ShipOperations shipsOperations() {
        return shipOperations;
    }

    public FieldOperations fieldOperations() {
        return battleFieldOperations;
    }

    public AutomaticShipPlacer automaticShipPlacer() {
        return automaticShipPlacer;
    }

    public ManualShipPlacer manualShipPlacer() {
        return manualShipPlacer;
    }

    public int getShipsCount() {
        return shipOperations.getShips().size();
    }

}
