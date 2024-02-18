package logic;

import data.Configuration;
import data.Ship;
import data.enums.ShipType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShipOperations {

    private List<Ship> ships = new ArrayList<>();

    public void removeShip(Ship ship) {
        ships.remove(ship);
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void generateShips() {
        for (int i = 0; i < Configuration.getLincorNumber(); i++) {
            ships.add(new Ship(ShipType.LINCOR.getType(), Configuration.getLincorDecks()));
        }
        for (int j = 0; j < Configuration.getCruiserNumber(); j++) {
            ships.add(new Ship(ShipType.CRUISER.getType(), Configuration.getCruiserDecks()));
        }
        for (int k = 0; k < Configuration.getDestroyerNumber(); k++) {
            ships.add(new Ship(ShipType.DESTROYER.getType(), Configuration.getDestroyerDecks()));
        }
        for (int y = 0; y < Configuration.getYachtNumber(); y++) {
            ships.add(new Ship(ShipType.YACHT.getType(), Configuration.getYachtDecks()));
        }
        for (int a = 0; a < Configuration.getArmadilloNumber();a++) {
            ships.add(new Ship(ShipType.ARMADILLO.getType(), Configuration.getArmadilloDecks()));
        }
        for (int l = 0; l < Configuration.getBoatNumber(); l++) {
            ships.add(new Ship(ShipType.BOAT.getType(), Configuration.getBoatDecks()));
        }
        ships.sort(Comparator.comparingInt(Ship::getShipDecks).reversed());
    }

    public void printShipsNumber() {
        long lincorCount = ships.stream().filter(ship -> ship.getShipType()
                .equals(ShipType.LINCOR.getType())).count();
        long cruiserCount = ships.stream().filter(ship -> ship.getShipType()
                .equals(ShipType.CRUISER.getType())).count();
        long destroyerCount = ships.stream().filter(ship -> ship.getShipType()
                .equals(ShipType.DESTROYER.getType())).count();
        long yachtCount = ships.stream().filter(ship -> ship.getShipType().equals(ShipType.YACHT.getType())).count();
        long armadilloCount = ships.stream().filter(ship -> ship.getShipType().equals(ShipType.ARMADILLO.getType())).count();
        long boatCount = ships.stream().filter(ship -> ship.getShipType()
                .equals(ShipType.BOAT.getType())).count();
        System.out.println("Enemy lincor number: " + lincorCount);
        System.out.println("Enemy cruisers number: " + cruiserCount);
        System.out.println("Enemy destroyers number: " + destroyerCount);
        System.out.println("Enemy yacht number: " + yachtCount);
        System.out.println("Enemy armadillo number: " + armadilloCount);
        System.out.println("Enemy boats number: " + boatCount);
        System.out.println();
    }
}
