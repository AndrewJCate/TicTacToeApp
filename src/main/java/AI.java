import java.util.List;
import java.util.Random;

public class AI {

    // Currently AI only chooses random available position
    public int getPlay(List<Integer> availablePositions) throws IllegalArgumentException {

        int randIndex = new Random().nextInt(availablePositions.size());

        return availablePositions.get(randIndex);

    }


}
