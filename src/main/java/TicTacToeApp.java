import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToeApp {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        boolean keepPlaying = true;

        while (keepPlaying) {

            System.out.println(Constants.WELCOME_MESSAGE);

            // Purposefully allow duplicate marks for a bit of fun.
            char playerMark = getMark(in, Constants.PLAYER_MARK_PROMPT);
            char aiMark = getMark(in, Constants.OPPONENT_MARK_PROMPT);

            Game game = new Game(playerMark, aiMark);

            AI ai = new AI();

            System.out.println(Constants.INSTRUCTIONS);

            game.printTurnOptions();

            System.out.println(Constants.BEGIN_MESSAGE);

            // Game begin
            while (!game.isGameOver()) {

                // Player turn
                if (game.getCurrentMark() == game.getPlayerMark()) {

                    int position = getPlayerTurn(in, game);

                    System.out.printf((Constants.YOUR_MOVE) + "%n", position);
                }

                // AI turn
                else {

                    System.out.println(Constants.AI_TURN);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // Do nothing
                    }

                    int position = ai.playTurn(game);
                    game.playTurn(position);

                    System.out.printf((Constants.AI_MOVE) + "%n", position);
                }

                game.printBoard();

            }

            // Game over
            printGameResult(game);

            keepPlaying = getKeepPlaying(in);
        }

        System.out.println(Constants.GOODBYE_MESSAGE);

        in.close();
    }

    private static char getMark(Scanner in, String prompt) {

        char mark;

        do {

            System.out.print(prompt);

            mark = in.nextLine().charAt(0);

            if (mark < 33 || mark > 126) {
                System.out.println(Constants.MARK_ERROR);
            }

        } while (mark < 33 || mark > 126);

        return mark;

    }

    private static int getInt(Scanner in, String prompt) {

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

    private static int getPlayerTurn(Scanner in, Game game) {

        int position = getInt(in, Constants.YOUR_TURN);

        in.nextLine();  // clear input

        while (!game.playTurn(position)) {

            System.out.println(Constants.TRY_AGAIN);

            game.printTurnOptions();

            position = getInt(in, Constants.YOUR_TURN);

            in.nextLine();  // clear input
        }

        return position;
    }

    private static boolean getKeepPlaying(Scanner in) {

        System.out.print(Constants.KEEP_PLAYING_PROMPT);

        String response = in.nextLine();

        return response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y");
    }

    private static void printGameResult(Game game) {

        if (game.getIsWinner()) {

            if (game.getWinner() == game.getPlayerMark()) {
                System.out.println(Constants.YOU_WIN);
            }
            else {
                System.out.println(Constants.AI_WIN);
            }

        }
        else if (game.getIsBoardFilled()) {
            System.out.println(Constants.TIE_MESSAGE);
        }

    }


}
