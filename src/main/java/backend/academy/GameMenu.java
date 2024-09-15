package backend.academy;

import java.util.Scanner;


public class GameMenu {
    public void startGame() {
        Scanner scanner = new Scanner(System.in);

        int correct_difficulty_choice = 0;
        System.out.println("Choose the difficulty of mystery word:");
        System.out.println("1 - easy\n2 - medium\n3 - hard");
        String difficulty_choice = scanner.next();

        //Проверка корректности ввода выбора. Средняя сложность иначе.
        try {
            correct_difficulty_choice = correctDifficultyChoice(difficulty_choice);
        } catch (InvalidNumberChoice e) {
            System.out.println(e.message());
            }

        String correct_category_choice = "";

        System.out.println("Choose the category of words:");
        System.out.println("animal");
        System.out.println("film");
        System.out.println("country");
        System.out.print("brand\n>");

        //Проверка корректности ввода выбора. Категория животных выставлена иначе.
        try {
            String category_choice = scanner.next();
            correct_category_choice = correctCategoryChoice(category_choice);

        } catch (InvalidWordException e) {
            System.out.println(e.message());
        }

        GameLogic game_settings = new GameLogic(correct_difficulty_choice, correct_category_choice);
        try {
            String answer = correctAnswer(game_settings.getWord());
            System.out.println("You can start guessing:");
            if (game_settings.gameProcess(answer, game_settings) > 0) {
                System.out.println("Congratulations, you won! My word was " + answer);
            } else {
                System.out.println("Lucky next time, you lost... My word was " + answer);
            }
        } catch (InvalidWordException e) {
            System.out.println(e.message());
        }

        scanner.close();
    }

    public int correctDifficultyChoice(String number_to_check) throws InvalidNumberChoice {
        //Проверка, что ввод состоит из чисел
        if (number_to_check.matches(("-?\\d+"))) {
            int difficulty_choice = Integer.parseInt(number_to_check);
            if (difficulty_choice >= 1 && difficulty_choice <= 3) {
                return difficulty_choice;
            } else {
                throw new InvalidNumberChoice("The difficulty need to be in range [1, 3]\nMedium difficulty is set anyway");
            }
        } else {
            throw new InvalidNumberChoice("The difficulty need to be the integer\nMedium difficulty is set anyway");
        }
    }

    public String correctCategoryChoice(String string_to_check) throws InvalidWordException {
        if (string_to_check.matches("[a-zA-Z]+")) {
            if (string_to_check.equals("animal") || string_to_check.equals("film") ||
                string_to_check.equals("brand") || string_to_check.equals("country")) {
                return string_to_check;
            } else {
                throw new InvalidWordException("The category need to be one of this suggested categories\nAnimal category is set anyway");
            }
        } else {
            throw new InvalidWordException("The category consists only from english letters\nAnimal category is set anyway");
        }
    }

    public String correctAnswer(String answer_to_check) throws InvalidWordException {
        if (!answer_to_check.isEmpty()) {
            if (answer_to_check.matches("[а-яА-ЯёЁ]+")) {
                return answer_to_check;
            }
            else {
                throw new InvalidWordException("Unpredictable word");
            }
        }
        else {
            throw new InvalidWordException("Incorrect length");
        }
    }
}
