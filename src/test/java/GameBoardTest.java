import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    static final int WIDTH = 3; // game board width

    final char MARK_A = 'a';
    final char MARK_B = 'b';

    GameBoard gameBoard;

    @BeforeEach
    void createGameBoard() {
        gameBoard = new GameBoard(WIDTH);
    }

    @Nested
    @DisplayName("checkForFullBoard() method tests")
    class checkForFullBoardNestedTests {

        @Test
        void checkThatEmptyBoardIsNotFull() {
            assertFalse(gameBoard.checkForFullBoard());
        }

        @Test
        void checkThatPartiallyFilledBoardIsNotFull() {

            gameBoard.markBoard(1, MARK_A);
            gameBoard.markBoard(4, MARK_A);
            gameBoard.markBoard(7, MARK_A);
            gameBoard.markBoard(9, MARK_A);

            assertFalse(gameBoard.checkForFullBoard());
        }

        @Test
        void checkThatFilledBoardIsFull() {

            final char mark = 'x';

            for (int i = 1; i <= 9; i++) {
                gameBoard.markBoard(i, mark);
            }

            assertTrue(gameBoard.checkForFullBoard());
        }
    }

    @Nested
    @DisplayName("checkForWinner() method tests")
    class checkForWinnerTests {

        @Test
        void should_be_false_for_empty_board() {
            assertFalse(gameBoard.checkForWinner());
        }

        @ParameterizedTest
        @MethodSource("allWinConditions")
        void should_be_true_for_winning_board(int[] listOfValues) {

            for (int value : listOfValues) {
                gameBoard.markBoard(value, MARK_A);
            }

            assertTrue(gameBoard.checkForWinner());
        }

        @ParameterizedTest
        @MethodSource("allWinConditions")
        void should_be_false_for_almost_winning_board(int[] listOfValues) {

            for (int i = 0; i < listOfValues.length; i++) {

                if (i == 0) {
                    gameBoard.markBoard(listOfValues[i], MARK_B);
                }
                else {
                    gameBoard.markBoard(listOfValues[i], MARK_A);
                }

            }

            gameBoard.printBoard();

            assertFalse(gameBoard.checkForWinner());
        }

        static int[][] allWinConditions() {

            int[][] listsOfValues = new int[(WIDTH * 2) + 2][WIDTH];

            int currentListIndex = 0;

            // Create all row winners
            for (int i = 1; i <= Math.pow(WIDTH, 2); i += WIDTH) {

                for (int j = 0; j < WIDTH; j++) {
                    listsOfValues[currentListIndex][j] = i + j;
                }

                currentListIndex++;
            }

            // Create all column winners
            for (int i = 1; i <= WIDTH; i++) {

                for (int j = 0; j < WIDTH; j++) {
                    listsOfValues[currentListIndex][j] = i + (WIDTH * j);
                }

                currentListIndex++;
            }

            // Create left diagonal winners
            for (int i = 1, j = 0; i <= Math.pow(WIDTH, 2); i += WIDTH + 1, j++) {
                listsOfValues[currentListIndex][j] = i;
            }

            currentListIndex++;

            // Create right diagonal winners
            for (int i = WIDTH, j = 0; i <= Math.pow(WIDTH, 2) - (WIDTH - 1); i += WIDTH - 1, j++) {
                listsOfValues[currentListIndex][j] = i;
            }

            return listsOfValues;

        }

    }

    @Nested
    @DisplayName("getAvailablePositions() method tests")
    class getAvailablePositionsTests {

        @Test
        void should_return_List_with_all_unique_elements_between_one_and_board_width_squared() {

            List<Integer> expectedValues = new ArrayList<>();

            for (int i = 1; i <= WIDTH * WIDTH; i++) {
                expectedValues.add(i);
            }

            assertEquals(expectedValues, gameBoard.getAvailablePositions());
        }

        @Test
        void should_return_empty_List() {

            List<Integer> expectedValues = new ArrayList<>();

            for (int i = 1; i <= WIDTH * WIDTH; i++) {

                if (i % 2 == 0) {
                    gameBoard.markBoard(i, MARK_A);
                }
                else {
                    gameBoard.markBoard(i, MARK_B);
                }

            }

            assertEquals(expectedValues, gameBoard.getAvailablePositions());
        }

        @Test
        void should_return_List_with_a_single_element() {

            List<Integer> expectedValues = new ArrayList<>();

            for (int i = 1; i <= WIDTH * WIDTH; i++) {

                if (i == WIDTH) {
                    gameBoard.markBoard(i, MARK_A);
                }
                else {
                    expectedValues.add(i);
                }
            }

            assertEquals(expectedValues, gameBoard.getAvailablePositions());
        }

        @Test
        void should_return_List_with_all_but_two_elements() {

            List<Integer> expectedValues = new ArrayList<>();

            for (int i = 2; i <= (WIDTH * WIDTH) - 1; i++) {
                expectedValues.add(i);
            }

            gameBoard.markBoard(1, MARK_A);
            gameBoard.markBoard(WIDTH * WIDTH, MARK_B);

            assertEquals(expectedValues, gameBoard.getAvailablePositions());
        }

    }

    @Disabled
    @Test
    void getBoardLength() {
        // TODO
    }

    @Nested
    @DisplayName("markBoard() method tests")
    class markBoardTests {

        @Test
        void should_return_false_when_position_below_one() {
            assertFalse(gameBoard.markBoard(0, MARK_A));
        }

        @Test
        void should_return_false_when_position_above_width_squared() {
            assertFalse(gameBoard.markBoard((WIDTH * WIDTH) + 1, MARK_A));
        }

        @Test
        void should_return_false_when_position_is_taken() {

            gameBoard.markBoard(WIDTH, MARK_A);

            assertFalse(gameBoard.markBoard(WIDTH, MARK_A));
        }

        @Test
        void should_return_true_when_position_is_one() {
            assertTrue(gameBoard.markBoard(1, MARK_A));
        }

        @Test
        void should_return_true_when_position_is_width_squared() {
            assertTrue(gameBoard.markBoard((WIDTH * WIDTH), MARK_A));
        }

    }

    @Nested
    @DisplayName("print out method tests")
    class printBoardTests {

        ByteArrayOutputStream outputStream;
        PrintStream printStream;
        PrintStream originalOut;


        @BeforeEach
        void setupOutputStream() {
            outputStream = new ByteArrayOutputStream();
            printStream = new PrintStream(outputStream);
            originalOut = System.out;

            System.setOut(printStream);
        }

        @AfterEach
        void restoreOutputStream() {
            System.out.flush();
            System.setOut(originalOut);
        }

        @Test
        void should_display_an_empty_board() {

            final String expectedDisplay =
               """
                \r
                   |   |  \r
                -----------\r
                   |   |  \r
                -----------\r
                   |   |  \r
                """;

            gameBoard.printBoard();

            assertEquals(expectedDisplay, outputStream.toString());

        }

        @Test
        void should_display_a_full_board() {

            final String expectedDisplay =
                    """
                     \r
                      b | a | b\r
                     -----------\r
                      a | b | a\r
                     -----------\r
                      b | a | b\r
                     """;

            for (int i = 1; i <= (WIDTH * WIDTH); i++) {

                if (i % 2 == 0) {
                    gameBoard.markBoard(i, MARK_A);
                }
                else {
                    gameBoard.markBoard(i, MARK_B);
                }
            }

            gameBoard.printBoard();

            assertEquals(expectedDisplay, outputStream.toString());
        }

        @Test
        void should_display_every_turn_option() {

            final String expectedDisplay =
                    """
                      1 | 2 | 3\r
                     -----------\r
                      4 | 5 | 6\r
                     -----------\r
                      7 | 8 | 9\r
                     """;

            gameBoard.printAvailableTurnOptions();

            assertEquals(expectedDisplay, outputStream.toString());
        }

        @Test
        void should_display_several_turn_options() {

            final String expectedDisplay =
                    """
                      1 |   | 3\r
                     -----------\r
                        | 5 |  \r
                     -----------\r
                      7 |   | 9\r
                     """;

            for (int i = 1; i <= (WIDTH * WIDTH); i++) {

                if (i % 2 == 0) {
                    gameBoard.markBoard(i, MARK_A);
                }
            }

            gameBoard.printAvailableTurnOptions();

            assertEquals(expectedDisplay, outputStream.toString());
        }

        @Test
        void should_display_no_turn_options() {

            final String expectedDisplay =
                    """
                        |   |  \r
                     -----------\r
                        |   |  \r
                     -----------\r
                        |   |  \r
                     """;

            for (int i = 1; i <= (WIDTH * WIDTH); i++) {

                if (i % 2 == 0) {
                    gameBoard.markBoard(i, MARK_A);
                }
                else {
                    gameBoard.markBoard(i, MARK_B);
                }
            }

            gameBoard.printAvailableTurnOptions();

            assertEquals(expectedDisplay, outputStream.toString());
        }
    }



}