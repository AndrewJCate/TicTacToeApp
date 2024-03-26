import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Random.class)
class AITest {

    AI ai;

    @BeforeEach
    void setup_AI() {
        ai = new AI();
    }

    @Test
    void playTurn_should_return_forty_two() {

        List<Integer> list = List.of(42);

        assertEquals(ai.getPlay(list), 42);
    }

    @Test
    void playTurn_should_throw_IllegalArgumentException() {

        List<Integer> list = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> ai.getPlay(list));

    }

    @RepeatedTest(50)
    void playTurn_should_return_a_number_included_in_the_argument_list() {

        List<Integer> list = List.of(1, 2, 4, 5);

        int result = ai.getPlay(list);

        assertTrue(list.contains(result));
    }
}