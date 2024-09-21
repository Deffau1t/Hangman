package backend.academy;

import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;


public class GameMenu {
    private static final Logger logger = Logger.getLogger(GameMenu.class.getName());

    public void startGame() {
        Scanner scanner = new Scanner(System.in);

        int corDifficultyChoice = 0;
        System.out.println("Choose the difficulty of mystery word:");
        System.out.println("1 - easy\n2 - medium\n3 - hard");
        String difficultyChoice = scanner.next();

        //Проверка корректности ввода выбора. Средняя сложность иначе.
        try {
            corDifficultyChoice = correctDifficultyChoice(difficultyChoice);
        } catch (InvalidNumberChoice e) {
            System.out.println(e.message());
            }

        String corCategoryChoice = "";

        System.out.println("Choose the category of words:");
        System.out.println("-animal");
        System.out.println("-film");
        System.out.println("-country");
        System.out.print("-brand\n>");

        //Проверка корректности ввода выбора. Категория животных выставлена иначе.
        try {
            String categoryChoice = scanner.next();
            corCategoryChoice = correctCategoryChoice(categoryChoice);

        } catch (InvalidWordException e) {
            System.out.println(e.message());
        }

        GameLogic gameSettings = new GameLogic(corDifficultyChoice, corCategoryChoice);
        try {
            String answer = correctAnswer(gameSettings.getWord());
            System.out.println("You can start guessing:");
            if (gameSettings.gameProcess(answer, gameSettings) > 0) {
                System.out.println("Congratulations, you won! My word was " + answer);
            } else {
                System.out.println("Lucky next time, you lost... My word was " + answer);
            }
        } catch (InvalidWordException e) {
            System.out.println(e.message());
        }

        scanner.close();
    }

    public int correctDifficultyChoice(String numberToCheck) throws InvalidNumberChoice {
        //Проверка, что ввод состоит из чисел
        if (numberToCheck.matches(("-?\\d+"))) {
            int difficultyChoice = Integer.parseInt(numberToCheck);
            if (difficultyChoice >= 1 && difficultyChoice <= 3) {
                return difficultyChoice;
            } else {
                throw new InvalidNumberChoice("The difficulty need to be in range [1, 3]\n"
                    + "Medium difficulty is set anyway");
            }
        } else {
            throw new InvalidNumberChoice("The difficulty need to be the integer\nMedium difficulty is set anyway");
        }
    }

    public String correctCategoryChoice(String stringToCheck) throws InvalidWordException {
        if (stringToCheck.matches("[a-zA-Z]+")) {
            if (stringToCheck.equals("animal") || stringToCheck.equals("film") ||
                stringToCheck.equals("brand") || stringToCheck.equals("country")) {
                return stringToCheck;
            } else {
                throw new InvalidWordException("The category need to be one of this suggested categories"
                    + "\nAnimal category is set anyway");
            }
        } else {
            throw new InvalidWordException("The category consists only from english letters"
                + "\nAnimal category is set anyway");
        }
    }

    public String correctAnswer(String answerToCheck) throws InvalidWordException {
        if (!answerToCheck.isEmpty()) {
            if (answerToCheck.matches("[а-яА-ЯёЁ]+")) {
                return answerToCheck;
            } else {
                throw new InvalidWordException("Unpredictable word");
            }
        } else {
            throw new InvalidWordException("Incorrect length");
        }
    }
}
