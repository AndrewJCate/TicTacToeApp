import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {

    /*
        Board layout

         0 | 1 | 2
        -----------
         3 | 4 | 5
        -----------
         6 | 7 | 8

        Turn options

         1 | 2 | 3
        -----------
         4 | 5 | 6
        -----------
         7 | 8 | 9
     */

    // TODO implement BOARD_WIDTH

    private final char[] GAME_BOARD;

    private final char AI_MARK;
    private final char PLAYER_MARK;
    private final char AVAILABLE_POSITION_MARK = ' ';

    private char currentMark;
    private char winner;
    private boolean isBoardFilled = false;
    private boolean isWinner = false;

    public Game(char playerMark, char aiMark) {

        this.PLAYER_MARK = playerMark;
        this.AI_MARK = aiMark;

        this.GAME_BOARD = setupBoard();
        this.setupFirstPlayer();

    }



    public boolean isGameOver() { return this.checkWinner() || this.checkBoardFilled(); }

    // Checks if there is a winner
    public boolean checkWinner() {

        boolean won = false;

        for (int i = 0; i < 3; i++) {

            if (isColWinner(i) || isRowWinner(i)) {

                won = true;

                break;
            }

        }

        if (isLeftDiagWinner() || isRightDiagWinner()) won = true;

        if (won) {

            this.setWinnerMark();

            this.isWinner = true;

        }

        return won;
    }

    public List<Integer> getAvailablePositions() {

        List<Integer> availablePositions = new ArrayList<>();

        for (int i = 0; i < this.GAME_BOARD.length; i++) {

            if (GAME_BOARD[i] == this.AVAILABLE_POSITION_MARK) {
                availablePositions.add(i + 1);  // AI sees choices as players do, from index 1
            }
        }

        return availablePositions;
    }

    public char getCurrentMark() {
        return this.currentMark;
    }

    public boolean getIsBoardFilled() { return this.isBoardFilled; }

    public boolean getIsWinner() { return this.isWinner; }

    public char getPlayerMark() {
        return this.PLAYER_MARK;
    }

    public char getWinner() { return this.winner; }

    // Accepts position player wants to put mark
    // Returns boolean of successful play
    public boolean playTurn(int position) {

        boolean isValid = inRange(position) && !isTaken(position);

        if (isValid) {

            // User sees indices from 1, not 0
            GAME_BOARD[position - 1] = this.currentMark;

            // Flip turn
            currentMark = (currentMark == PLAYER_MARK) ? AI_MARK : PLAYER_MARK;

            return true;
        }

        return false;
    }

    public void printBoard() {

        System.out.println();

        for (int i = 1; i <= GAME_BOARD.length; i++) {

            System.out.print(" " + GAME_BOARD[i - 1]);

            if (i % 3 != 0) {
                System.out.print(" |");
            }

            if (i % 3 == 0 && i != GAME_BOARD.length) {

                System.out.println();
                System.out.println("-----------");

            }
        }

        System.out.println();
    }

    // Prints current state of board with numbers
    public void printTurnOptions() {

        for (int i = 1; i <= GAME_BOARD.length; i++) {

            if (GAME_BOARD[i - 1] != AVAILABLE_POSITION_MARK) {
                System.out.print(" " + AVAILABLE_POSITION_MARK);
            }
            else {
                System.out.print(" " + i);
            }

            if (i % 3 != 0) {
                System.out.print(" |");
            }

            if (i % 3 == 0 && i != GAME_BOARD.length) {

                System.out.println();
                System.out.println("-----------");

            }
        }

        System.out.println();
    }



    private boolean inRange(int position) {
        return position > 0 && position < GAME_BOARD.length + 1;
    }

    private boolean checkBoardFilled() {

        for (char c : GAME_BOARD) if (c == AVAILABLE_POSITION_MARK) return false;

        this.isBoardFilled = true;

        return true;
    }

    // Col is 0 indexed
    private boolean isColWinner(int col) {

        if (GAME_BOARD[col] == AVAILABLE_POSITION_MARK) return false;

        return GAME_BOARD[col] == GAME_BOARD[col + 3] && GAME_BOARD[col] == GAME_BOARD[col + 6];
    }

    private boolean isLeftDiagWinner() {

        if (GAME_BOARD[0] == AVAILABLE_POSITION_MARK) return false;

        return GAME_BOARD[0] == GAME_BOARD[4] && GAME_BOARD[0] == GAME_BOARD[GAME_BOARD.length - 1];
    }

    private boolean isRightDiagWinner() {

        if (GAME_BOARD[2] == AVAILABLE_POSITION_MARK) return false;

        return GAME_BOARD[2] == GAME_BOARD[4] && GAME_BOARD[2] == GAME_BOARD[6];
    }

    // Row is 0 indexed
    private boolean isRowWinner(int row) {

        row = (row + 1) * 3 - 1;

        if (GAME_BOARD[row] == AVAILABLE_POSITION_MARK) return false;

        return GAME_BOARD[row] == GAME_BOARD[row - 1] && GAME_BOARD[row] == GAME_BOARD[row - 2];
    }

    private boolean isTaken(int position) {
        return GAME_BOARD[position - 1] != AVAILABLE_POSITION_MARK;
    }

    // Creates a blank game board
    private char[] setupBoard() {

        char[] board = new char[9];

        Arrays.fill(board, AVAILABLE_POSITION_MARK);

        return board;
    }

    private void setupFirstPlayer() {

        int randInt = new Random().nextInt(2);

        if (randInt == 0) {
            currentMark = PLAYER_MARK;
        }
        else {
            currentMark = AI_MARK;
        }
    }

    private void setWinnerMark() {

        if (currentMark == PLAYER_MARK) {
            this.winner = AI_MARK;
        }
        else {
            this.winner = PLAYER_MARK;
        }
    }

}
