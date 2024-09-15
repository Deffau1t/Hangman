package backend.academy;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {
    private GameLogic game;
    private GameData data;

    @Before
    public void setUp() {
        game = new GameLogic();
        data = new GameData();
    }

    @Test
    public void randomWordPickCase() {
        String filmHardExample = "некромантия";

        game.category("film");
        game.difficulty(3);
        String [] firstCaseWords = game.wordList();
        assertTrue(Arrays.asList(firstCaseWords).contains(filmHardExample));

        game.difficulty(2);
        firstCaseWords = game.wordList();
        assertFalse(Arrays.asList(firstCaseWords).contains(filmHardExample));

        game.category("animal");
        firstCaseWords = game.wordList();
        assertFalse(Arrays.asList(firstCaseWords).contains(filmHardExample));

        String animalEasyExample = "птица";

        game.category("animal");
        game.difficulty(1);
        String [] secondCaseWords = game.wordList();
        assertTrue(Arrays.asList(secondCaseWords).contains(animalEasyExample));

        game.attempts(3);
        secondCaseWords = game.wordList();
        assertTrue(Arrays.asList(secondCaseWords).contains(animalEasyExample));

        game.difficulty(3);
        secondCaseWords = game.wordList();
        assertFalse(Arrays.asList(secondCaseWords).contains(animalEasyExample));
    }

    @Test
    public void CorrectIllustrationCase() {
        game.difficulty(1);
        game.attempts(3);
        String firstIllustration = data.gameVisualStages(game.attempts(), game.difficulty());
        String correctFirstIllustration = "=========" + "       \n" +
            "  |   |\n" +
            "  O   |\n" +
            " /|\\  |\n" +
            "      |\n" +
            "      |\n" +
            "=========";
        assertEquals(firstIllustration, correctFirstIllustration);

        game.difficulty(2);
        game.attempts(5);
        String secondIllustration = data.gameVisualStages(game.attempts(), game.difficulty());
        String correctSecondIllustration = "=========" + "       \n" +
            "  |   |\n" +
            "  O   |\n" +
            "  |   |\n" +
            "      |\n" +
            "      |\n" +
            "=========";
        assertEquals(secondIllustration, correctSecondIllustration);

        game.difficulty(3);
        game.attempts(4);
        String thirdIllustration = data.gameVisualStages(game.attempts(), game.difficulty());
        String correctThirdIllustration = "=========" + "       \n" +
            "  |   |\n" +
            "  O   |\n" +
            " /|   |\n" +
            "      |\n" +
            "      |\n" +
            "=========";
        assertEquals(thirdIllustration, correctThirdIllustration);
    }

}
