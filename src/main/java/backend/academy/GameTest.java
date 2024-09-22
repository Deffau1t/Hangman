package backend.academy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
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

    //Проверка правильности выбора слова из списка
    @SuppressWarnings("3")
    @Test
    public void randomWordPickCase() {
        final String FILM_HARD_EXAMPLE = "некромантия";
        final int HARD_DIFFICULTY = 3;
        final String ANIMAL_CATEGORY = "animal";
        game.category("film");
        game.difficulty(HARD_DIFFICULTY);
        String[] firstCaseWords = game.wordList();
        assertTrue(Arrays.asList(firstCaseWords).contains(FILM_HARD_EXAMPLE));

        game.difficulty(2);
        firstCaseWords = game.wordList();
        assertFalse(Arrays.asList(firstCaseWords).contains(FILM_HARD_EXAMPLE));

        game.category(ANIMAL_CATEGORY);
        firstCaseWords = game.wordList();
        assertFalse(Arrays.asList(firstCaseWords).contains(FILM_HARD_EXAMPLE));

        String animalEasyExample = "птица";
        game.category(ANIMAL_CATEGORY);
        game.difficulty(1);
        String[] secondCaseWords = game.wordList();
        assertTrue(Arrays.asList(secondCaseWords).contains(animalEasyExample));

        game.attempts(3);
        secondCaseWords = game.wordList();
        assertTrue(Arrays.asList(secondCaseWords).contains(animalEasyExample));

        game.difficulty(3);
        secondCaseWords = game.wordList();
        assertFalse(Arrays.asList(secondCaseWords).contains(animalEasyExample));
    }

    //Проверка корректности отображения состояния игры после каждого ввода пользователя
    @SuppressWarnings({"3", "4", "5"})
    @Test
    public void correctIllustrationCase() {
        game.difficulty(1);
        game.attempts(4);
        String firstIllustration = data.gameVisualStages(game.attempts(), game.difficulty());
        String floor = "=========";
        String indent = "       \n";
        String headIndent = "  O   |\n";
        String lowGroundIndent = "      |\n";
        String leftHandIndent = " /|   |\n";
        String mediumIndent = "  |   |\n";
        String correctFirstIllustration =
            floor + indent
            + mediumIndent
            + headIndent
            + leftHandIndent
            + lowGroundIndent
            + lowGroundIndent
            + floor;
        assertEquals(firstIllustration, correctFirstIllustration);

        game.difficulty(2);
        game.attempts(5);
        String secondIllustration = data.gameVisualStages(game.attempts(), game.difficulty());
        String correctSecondIllustration =
            floor + indent
            + mediumIndent
            + headIndent
            + mediumIndent
            + lowGroundIndent
            + lowGroundIndent
            + floor;
        assertEquals(secondIllustration, correctSecondIllustration);

        game.difficulty(3);
        game.attempts(5);
        String thirdIllustration = data.gameVisualStages(game.attempts(), game.difficulty());
        assertEquals(thirdIllustration, correctSecondIllustration);
    }

    //Проверка, что введенные буквы корректно обрабатываются вне зависимости от их регистра.
    @SuppressWarnings("5")
    @Test
    public void letterRegisterCase() {
        String answerCase = "ПроВерка";

        List<Integer> firstCorrectIndexList = new ArrayList<>();
        firstCorrectIndexList.add(2);

        List<Integer> firstResultList = game.foundLetter('о', answerCase);
        assertEquals(firstResultList, firstCorrectIndexList);

        List<Integer> secondResultList = game.foundLetter('О', answerCase);
        assertEquals(secondResultList, firstCorrectIndexList);

        List<Integer> secondCorrectIndexList = new ArrayList<>();
        secondCorrectIndexList.add(1);
        secondCorrectIndexList.add(5);
        List<Integer> thirdResultList = game.foundLetter('р', answerCase);
        assertEquals(thirdResultList, secondCorrectIndexList);

        List<Integer> fourthResultList = game.foundLetter('Р', answerCase);
        assertEquals(fourthResultList, secondCorrectIndexList);
    }
}
