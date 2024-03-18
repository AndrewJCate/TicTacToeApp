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

            game.playGame(in);

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

    private static boolean getKeepPlaying(Scanner in) {

        System.out.print(Constants.KEEP_PLAYING_PROMPT);

        String response = in.nextLine();

        return response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y");
    }

}
