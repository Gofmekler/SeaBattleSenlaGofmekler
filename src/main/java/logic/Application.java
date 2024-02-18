package logic;

import data.Player;

import java.util.Scanner;

public class Application {

    private static boolean shouldApplicationFinish = false;

    public static void setApplicationExitCondition() {
        shouldApplicationFinish = true;
    }

    public void run() {

        try (Scanner scanner = new Scanner(System.in)){
            String nickName = new InputOperator().enterName(scanner);
            Player humanPlayer = new Player(nickName);
            Player botPlayer = new Player();
            if (new InputOperator().isManualPlacementRequired(scanner)) {
                humanPlayer.manualShipPlacer().placeShips(humanPlayer.shipsOperations().getShips(), scanner);
            } else {
                humanPlayer.automaticShipPlacer().placeShips(humanPlayer.shipsOperations().getShips());
            }
            botPlayer.automaticShipPlacer().placeShips(botPlayer.shipsOperations().getShips());

            if (!shouldApplicationFinish) {
                Battle seaBattle = new Battle(humanPlayer, botPlayer, scanner);
                seaBattle.startBattle();
            }
        } catch (InterruptedException intEx) {
            System.err.println("There is a problem with thread");
        } catch (Exception ex) {
            System.err.println("Something went wrong " + ex.getMessage());
        }
    }
}
