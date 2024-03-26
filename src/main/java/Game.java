import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private final char AI_MARK;
    private final char PLAYER_MARK;
    private final GameBoard gameBoard;

    private char currentPlayerMark;
    private boolean isWinner = false;
    private char winnerMark;

    public Game(char playerMark, char aiMark) {

        PLAYER_MARK = playerMark;
        AI_MARK = aiMark;

        gameBoard = new GameBoard();
        chooseWhoGoesFirst();

    }

    public void playGame(Scanner in) {

        AI ai = new AI();

        System.out.println(Constants.INSTRUCTIONS);

        gameBoard.printAvailableTurnOptions();

        System.out.println(Constants.BEGIN_MESSAGE);

        // Game begin
        while (!checkGameOver()) {

            // Player turn
            if (currentPlayerMark == PLAYER_MARK) {

                int position = getPlayerTurn(in);

                System.out.printf((Constants.YOUR_MOVE) + "%n", position);
            }

            // AI turn
            else {

                System.out.println(Constants.AI_TURN);

                // Pause to simulate thinking and give player time to process action
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // Do nothing
                }

                int position = ai.getPlay(gameBoard.getAvailablePositions());

                playTurn(position);

                System.out.printf((Constants.AI_MOVE) + "%n", position);
            }

            gameBoard.printBoard();

        }

        // Game over
        printGameResult();
    }

    // Accepts position player wants to put mark
    // Returns boolean of successful play
    public boolean playTurn(int position) {

        boolean isValidPlay = gameBoard.markBoard(position, currentPlayerMark);

        if (isValidPlay) {

            // Flip turn
            currentPlayerMark = (currentPlayerMark == PLAYER_MARK) ? AI_MARK : PLAYER_MARK;

            return true;
        }

        return false;
    }

    private boolean checkGameOver() {

        if (gameBoard.checkForWinner()) {

            setWinnerMark();

            isWinner = true;

            return true;
        }

        return gameBoard.checkForFullBoard();
    }

    private void chooseWhoGoesFirst() {

        int randInt = new Random().nextInt(2);

        if (randInt == 0) {
            currentPlayerMark = PLAYER_MARK;
        } else {
            currentPlayerMark = AI_MARK;
        }
    }

    private int getPlayerTurn(Scanner in) {

        int position = getPositionInput(in, Constants.YOUR_TURN);

        in.nextLine();  // clear input

        while (!playTurn(position)) {

            System.out.println(Constants.TRY_AGAIN);

            gameBoard.printAvailableTurnOptions();

            position = getPositionInput(in, Constants.YOUR_TURN);

            in.nextLine();  // clear input
        }

        return position;
    }

    private int getPositionInput(Scanner in, String prompt) {

        System.out.print(prompt);

        int input = -1;

        try {

            input = in.nextInt();

        }
        catch (InputMismatchException ex) {
            // Do nothing, input is checked elsewhere for correctness.
        }

        return input;

    }

    private void printGameResult() {

        if (isWinner) {

            if (winnerMark == PLAYER_MARK) {
                System.out.println(Constants.YOU_WIN);
            }
            else {
                System.out.println(Constants.AI_WIN);
            }

        }
        else if (gameBoard.checkForFullBoard()) {
            System.out.println(Constants.TIE_MESSAGE);
        }

    }

    private void setWinnerMark() {

        if (currentPlayerMark == PLAYER_MARK) {
            winnerMark = AI_MARK;
        } else {
            winnerMark = PLAYER_MARK;
        }
    }

}
