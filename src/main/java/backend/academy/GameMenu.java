package backend.academy;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import static backend.academy.GameLogic.getRandomCategory;

public class GameMenu {
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        PrintStream out = System.out;

        int corDifficultyChoice = 2;
        out.println("Choose the difficulty of mystery word:");
        out.println("1 - easy\n2 - medium\n3 - hard");
        String difficultyChoice = scanner.next();
        //Проверка корректности ввода выбора. Средняя сложность иначе.
        try {
            corDifficultyChoice = correctDifficultyChoice(difficultyChoice);
        } catch (backend.academy.InvalidNumberChoice e) {
            out.println(e.message());
        }

        String corCategoryChoice = getRandomCategory();
        out.print("""
                        Choose the category of words:
                        -animal
                        -film
                        -country
                        -brand
                        >""");
        //Проверка корректности ввода выбора. Категория животных выставлена иначе.
        try {
            String categoryChoice = scanner.next();
            corCategoryChoice = correctCategoryChoice(categoryChoice.toLowerCase());
        } catch (backend.academy.InvalidWordException e) {
            out.println(e.message());
        }

        GameLogic gameSettings = new GameLogic(corDifficultyChoice, corCategoryChoice);
        try {
            String answer = correctAnswer(gameSettings.getWord());
            out.println("You can start guessing");
            if (gameSettings.gameProcess(answer, gameSettings) > 0) {
                out.println("Congratulations, you won! My word was " + answer);
            } else {
                out.println("Lucky next time, you lost... My word was " + answer);
            }
        } catch (backend.academy.InvalidWordException e) {
            out.println(e.message());
        }
        scanner.close();
    }

    @SuppressWarnings("MagicNumber")
    public int correctDifficultyChoice(String numberToCheck) throws backend.academy.InvalidNumberChoice {
        //Проверка, что ввод состоит из чисел
        if (numberToCheck.matches(("-?\\d+"))) {
            int difficultyChoice = Integer.parseInt(numberToCheck);
            if (difficultyChoice >= 1 && difficultyChoice <= 3) {
                return difficultyChoice;
            } else {
                throw new backend.academy.InvalidNumberChoice("The difficulty need to be in range [1, 3]\n"
                    + "Medium difficulty is set anyway");
            }
        } else {
            throw new backend.academy.InvalidNumberChoice("The difficulty need to be the integer\nMedium difficulty is set anyway");
        }
    }

    public String correctCategoryChoice(String stringToCheck) throws backend.academy.InvalidWordException {
        if (List.of("animal", "film", "country", "brand").contains(stringToCheck)) {
                return stringToCheck;
        } else {
            throw new backend.academy.InvalidWordException("The category need to be one of this suggested categories"
                + "\nRandom category is set anyway");
        }
    }

    static String correctAnswer(String answerToCheck) throws backend.academy.InvalidWordException {
        if (!answerToCheck.isEmpty()) {
            if (answerToCheck.matches("[а-яА-ЯёЁ]+")) {
                return answerToCheck;
            } else {
                throw new backend.academy.InvalidWordException("Unpredictable word");
            }
        } else {
            throw new backend.academy.InvalidWordException("Incorrect length");
        }
    }
}
