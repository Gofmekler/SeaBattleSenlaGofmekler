package logic;

import data.Configuration;
import data.Player;
import data.enums.SystemMessages;

import java.util.Scanner;

import static utils.RandomNumberGenerator.generateRandomCoordinates;

public class Battle {

    private final Player humanPlayer;
    private final Player botPlayer;
    private final InputOperator inputOperator;
    private static boolean isGameFinished = false;
    private final PlayerShootingUtil playerShootingUtil;
    private final BotShootingUtil botShootingUtil;
    private final FieldPrinter fieldPrinter;
    private final Scanner scanner;

    public Battle(Player humanPlayer, Player botPlayer, Scanner scanner) {
        this.humanPlayer = humanPlayer;
        this.botPlayer = botPlayer;
        this.inputOperator = new InputOperator();
        this.playerShootingUtil = new PlayerShootingUtil(botPlayer);
        this.botShootingUtil = new BotShootingUtil(humanPlayer);
        this.fieldPrinter = new FieldPrinter();
        this.scanner = scanner;
    }

    public static void setExitCondition() {
        isGameFinished = true;
    }

    public void startBattle() throws InterruptedException {
        fieldPrinter.printDoubleField(humanPlayer.getField(), botPlayer.getField());

        while (!isGameFinished) {
            boolean isPlayerMove = true;
            boolean isBotMove = true;

            while (isPlayerMove && !isGameFinished) {
                botPlayer.shipsOperations().printShipsNumber();
                int[] shotCoordinates = inputOperator.enterIndexes(scanner, botPlayer, playerShootingUtil);
                if (isGameFinished) {
                    break;
                }
                isPlayerMove = playerShootingUtil.shot(shotCoordinates);
                fieldPrinter.printDoubleField(humanPlayer.getField(), botPlayer.getField());
                if (checkFinishGameConditions()) {
                    isGameFinished = true;
                    break;
                }
            }
            while (isBotMove && !isGameFinished) {
                Thread.sleep(3000);
                isBotMove = botShootingUtil.shot(generateRandomCoordinates(Configuration.getFieldSize()));
                fieldPrinter.printDoubleField(humanPlayer.getField(), botPlayer.getField());
                if (checkFinishGameConditions()) {
                    isGameFinished = true;
                    break;
                }
            }
        }
        determineTheWinner();
    }

    private boolean checkFinishGameConditions() {
        return humanPlayer.getShipsCount() == 0 || botPlayer.getShipsCount() == 0;
    }

    private void determineTheWinner() {
        if (humanPlayer.getShipsCount() == 0) {
            System.out.println(SystemMessages.botWin.getMessage());
        }
        if (botPlayer.getShipsCount() == 0) {
            System.out.println(SystemMessages.playerWin.getMessage());
        }
    }
}
