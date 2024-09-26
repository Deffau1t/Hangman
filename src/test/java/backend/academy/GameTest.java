package backend.academy;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static backend.academy.GameMenu.correctAnswer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
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
    @SuppressWarnings("MagicNumber")
    @Test
    public void randomWordPickCase() {
        final String FILM_HARD_EXAMPLE = "некромантия";
        final int HARD_DIFFICULTY = 3;
        final String ANIMAL_CATEGORY = "animal";
        game.category("film");
        game.difficulty(HARD_DIFFICULTY);
        List<String> firstCaseWords = game.wordList();
        assertTrue(firstCaseWords.contains(FILM_HARD_EXAMPLE));

        game.difficulty(2);
        firstCaseWords = game.wordList();
        assertFalse(firstCaseWords.contains(FILM_HARD_EXAMPLE));

        game.category(ANIMAL_CATEGORY);
        firstCaseWords = game.wordList();
        assertFalse(firstCaseWords.contains(FILM_HARD_EXAMPLE));

        String animalEasyExample = "птица";
        game.category(ANIMAL_CATEGORY);
        game.difficulty(1);
        List<String> secondCaseWords = game.wordList();
        assertTrue(secondCaseWords.contains(animalEasyExample));

        game.attempts(3);
        secondCaseWords = game.wordList();
        assertTrue(secondCaseWords.contains(animalEasyExample));

        game.difficulty(3);
        secondCaseWords = game.wordList();
        assertFalse(secondCaseWords.contains(animalEasyExample));
    }

    //Проверка корректности отображения состояния игры после каждого ввода пользователя
    @SuppressWarnings("MagicNumber")
    @Test
    public void correctIllustrationCase() {
        final int EASY_DIFFICULTY = 1;
        final int ILLUSTRATION_CASE_ATTEMPTS = 4;
        game.difficulty(EASY_DIFFICULTY);
        game.attempts(ILLUSTRATION_CASE_ATTEMPTS);

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
    }

    //Проверка, что введенные буквы корректно обрабатываются вне зависимости от их регистра.
    @Test
    public void letterRegisterCase() {
        String answerCase = "ПроВерка";

        final int indexOfLetterO = 2;
        List<Integer> firstCorrectIndexList = List.of(indexOfLetterO);

        List<Integer> firstResultList = game.foundLetter('о', answerCase);
        assertEquals(firstResultList, firstCorrectIndexList);

        List<Integer> secondResultList = game.foundLetter('О', answerCase);
        assertEquals(secondResultList, firstCorrectIndexList);

        final int firstIndexOfLetterP = 1;
        final int secondIndexOfLetterP = 5;
        List<Integer> secondCorrectIndexList = List.of(firstIndexOfLetterP, secondIndexOfLetterP);
        List<Integer> thirdResultList = game.foundLetter('р', answerCase);
        assertEquals(thirdResultList, secondCorrectIndexList);

        List<Integer> fourthResultList = game.foundLetter('Р', answerCase);
        assertEquals(fourthResultList, secondCorrectIndexList);
    }

    //Игра не запускается, если загадываемое слово имеет некорректную длину.
    @Test
    public void gameStartCase() {
        //Если во время исполнения ответ не пройдет эту проверку, то игра не запустится
        assertThrows(backend.academy.InvalidWordException.class, () -> correctAnswer(""));
    }

    //После превышения заданного количества попыток игра всегда возвращает поражение.
    @Test
    public void loseConditionCase() {
        String anyCorrectAnswer = "фламинго";
        game.attempts(0);
        assertTrue(game.gameProcess(anyCorrectAnswer, game) <= 0);

        String anyIncorrectAnswer = "incorrectAnswer";
        assertTrue(game.gameProcess(anyIncorrectAnswer, game) <= 0);

        game.attempts(-1);
        assertTrue(game.gameProcess(anyIncorrectAnswer, game) <= 0);
    }

    //Состояние игры корректно изменяется при угадывании/не угадывании.
    @Test
    public void gameStageCase() {
        final int STAGE_CASE_FIRST_ATTEMPTS = 5;
        final int STAGE_CASE_SECOND_ATTEMPTS = 4;
        final int STAGE_CASE_THIRD_ATTEMPTS = 3;
        final int STAGE_CASE_FOURTH_ATTEMPTS = 2;
        game.attempts(STAGE_CASE_FIRST_ATTEMPTS);
        String answer = "Аргентина";
        game.wordCheck(answer, "Аргинтина");
        assertEquals(STAGE_CASE_SECOND_ATTEMPTS, game.attempts());

        game.wordCheck(answer, "Месси");
        assertEquals(STAGE_CASE_THIRD_ATTEMPTS, game.attempts());

        game.foundLetter('а', answer);
        assertEquals(STAGE_CASE_THIRD_ATTEMPTS, game.attempts());

        game.foundLetter('о', answer);
        assertEquals(STAGE_CASE_FOURTH_ATTEMPTS, game.attempts());

        game.wordCheck(answer, "аргентина");
        assertEquals(STAGE_CASE_FOURTH_ATTEMPTS, game.attempts());
    }

    //Проверка, что при отгадывании ввод строки длиной больше чем 1 (опечатка) приводит к повторному вводу
    //без изменения состояния.
    @Test
    public void correctLetterCase() {
        //На вход функции проверки подается корректная уже буква, поэтому для проверки состояния игры
        // достаточно проверить ее на момент выполнения correctLetterPrediction
        final int LETTER_CASE_START_ATTEMPTS = 4;
        StringBuilder alphabet = new StringBuilder("абвгдеёжзийклмнопрстуфхцчшщъыьэюя");
        game.attempts(LETTER_CASE_START_ATTEMPTS);
        assertThrows(backend.academy.InvalidLetterException.class, () -> game.correctLetterPrediction("ап", alphabet));
        assertEquals(LETTER_CASE_START_ATTEMPTS, game.attempts());

        assertThrows(backend.academy.InvalidLetterException.class, () -> game.correctLetterPrediction("tdf", alphabet));
        assertEquals(LETTER_CASE_START_ATTEMPTS, game.attempts());
    }
}
