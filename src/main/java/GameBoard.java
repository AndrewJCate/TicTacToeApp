import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameBoard {

    /*
        Board index layout for 3x3

         0 | 1 | 2
        -----------
         3 | 4 | 5
        -----------
         6 | 7 | 8

        Turn options displayed for 3x3

         1 | 2 | 3
        -----------
         4 | 5 | 6
        -----------
         7 | 8 | 9
     */

    private final char[] GAME_BOARD;
    private final int boardWidth;

    public GameBoard(int boardWidth) {

        this.boardWidth = boardWidth;

        GAME_BOARD = setupNewGameBoard();
    }

    public boolean checkForFullBoard() {

        for (char c : GAME_BOARD) if (c == Constants.AVAILABLE_POSITION_MARK) return false;

        return true;
    }

    // Checks if there is a winner
    public boolean checkForWinner() {

        boolean isWinner = false;

        for (int i = 0; i < 3; i++) {

            if (checkForColumnWinner(i) || checkForRowWinner(i)) {

                isWinner = true;

                break;
            }

        }

        if (checkForLeftDiagonalWinner() || checkForRightDiagonalWinner()) isWinner = true;

        return isWinner;
    }

    public List<Integer> getAvailablePositions() {

        List<Integer> availablePositions = new ArrayList<>();

        for (int i = 0; i < GAME_BOARD.length; i++) {

            if (GAME_BOARD[i] == Constants.AVAILABLE_POSITION_MARK) {
                availablePositions.add(i + 1);  // AI sees choices as players do, from index 1
            }
        }

        return availablePositions;
    }

    public int getBoardLength() {
        return GAME_BOARD.length;
    }

    public boolean markBoard(int position, char mark) {

        boolean isValidPosition = checkIfValidPlay(position);

        if (isValidPosition) {

            GAME_BOARD[position - 1] = mark;

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
    public void printAvailableTurnOptions() {

        for (int i = 1; i <= GAME_BOARD.length; i++) {

            if (GAME_BOARD[i - 1] != Constants.AVAILABLE_POSITION_MARK) {
                System.out.print(" " + Constants.AVAILABLE_POSITION_MARK);
            } else {
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

    // Col is 0 indexed
    private boolean checkForColumnWinner(int col) {

        if (GAME_BOARD[col] == Constants.AVAILABLE_POSITION_MARK) return false;

        return GAME_BOARD[col] == GAME_BOARD[col + 3] && GAME_BOARD[col] == GAME_BOARD[col + 6];
    }

    private boolean checkForLeftDiagonalWinner() {

        if (GAME_BOARD[0] == Constants.AVAILABLE_POSITION_MARK) return false;

        return GAME_BOARD[0] == GAME_BOARD[4] && GAME_BOARD[0] == GAME_BOARD[GAME_BOARD.length - 1];
    }

    private boolean checkForRightDiagonalWinner() {

        if (GAME_BOARD[2] == Constants.AVAILABLE_POSITION_MARK) return false;

        return GAME_BOARD[2] == GAME_BOARD[4] && GAME_BOARD[2] == GAME_BOARD[6];
    }

    // Row is 0 indexed
    private boolean checkForRowWinner(int row) {

        row = (row + 1) * 3 - 1;

        if (GAME_BOARD[row] == Constants.AVAILABLE_POSITION_MARK) return false;

        return GAME_BOARD[row] == GAME_BOARD[row - 1] && GAME_BOARD[row] == GAME_BOARD[row - 2];
    }

    private boolean checkIfPositionInAcceptableRange(int position) {
        return position > 0 && position <= GAME_BOARD.length;
    }

    private boolean checkIfPositionIsTaken(int position) {
        return GAME_BOARD[position - 1] != Constants.AVAILABLE_POSITION_MARK;
    }

    private boolean checkIfValidPlay(int position) {

        return checkIfPositionInAcceptableRange(position) &&
                !checkIfPositionIsTaken(position);
    }

    // Creates a blank game board
    private char[] setupNewGameBoard() {

        char[] board = new char[9];

        Arrays.fill(board, Constants.AVAILABLE_POSITION_MARK);

        return board;
    }

}
