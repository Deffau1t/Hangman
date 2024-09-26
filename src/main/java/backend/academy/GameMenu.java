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
        out.println("Выберите сложность игры:");
        out.println("1 - легкая\n2 - средняя\n3 - сложная");
        String difficultyChoice = scanner.next();
        //Проверка корректности ввода выбора. Средняя сложность иначе.
        try {
            corDifficultyChoice = correctDifficultyChoice(difficultyChoice);
        } catch (backend.academy.hangmanExceptions.InvalidNumberChoice e) {
            out.println(e.message());
        }

        String corCategoryChoice = getRandomCategory();
        out.print("""
                        Выберите и напишите категорию слов из предложенных:
                        -animal
                        -film
                        -country
                        -brand
                        >""");
        //Проверка корректности ввода выбора. Категория животных выставлена иначе.
        try {
            String categoryChoice = scanner.next();
            corCategoryChoice = correctCategoryChoice(categoryChoice.toLowerCase());
        } catch (backend.academy.hangmanExceptions.InvalidWordException e) {
            out.println(e.message());
        }

        GameLogic gameSettings = new GameLogic(corDifficultyChoice, corCategoryChoice);
        try {
            String answer = correctAnswer(gameSettings.getWord());
            if (gameSettings.gameProcess(answer, gameSettings) > 0) {
                out.println("Поздравляю, Вы победили! Я загадал " + answer);
            } else {
                out.println("В следующий раз повезет, вы проиграли :( Я загадал " + answer);
            }
        } catch (backend.academy.hangmanExceptions.InvalidWordException e) {
            out.println(e.message());
        }
        scanner.close();
    }

    @SuppressWarnings("MagicNumber")
    public int correctDifficultyChoice(String numberToCheck) throws
        backend.academy.hangmanExceptions.InvalidNumberChoice {
        //Проверка, что ввод состоит из чисел
        if (numberToCheck.matches(("-?\\d+"))) {
            int difficultyChoice = Integer.parseInt(numberToCheck);
            if (difficultyChoice >= 1 && difficultyChoice <= 3) {
                return difficultyChoice;
            } else {
                throw new
                    backend.academy.hangmanExceptions.InvalidNumberChoice("Сложность должна быть в пределах [1, 3]\n"
                    + "Тем не менее, выставлена средняя сложность");
            }
        } else {
            throw new
                backend.academy.hangmanExceptions.InvalidNumberChoice("Нужно было ввести число"
                + "\nТем не менее, выставлена средняя сложность");
        }
    }

    public String correctCategoryChoice(String stringToCheck) throws
        backend.academy.hangmanExceptions.InvalidWordException {
        if (List.of("animal", "film", "country", "brand").contains(stringToCheck)) {
                return stringToCheck;
        } else {
            throw new backend.academy.hangmanExceptions.InvalidWordException("Категория не совпадает с предложенными"
                + "\nВыбрана случайная категория");
        }
    }

    static String correctAnswer(String answerToCheck) throws backend.academy.hangmanExceptions.InvalidWordException {
        if (!answerToCheck.isEmpty()) {
            if (answerToCheck.matches("[а-яА-ЯёЁ]+")) {
                return answerToCheck;
            } else {
                throw new backend.academy.hangmanExceptions.InvalidWordException("Это слово не угадать");
            }
        } else {
            throw new backend.academy.hangmanExceptions.InvalidWordException("Некорректная длина");
        }
    }
}
