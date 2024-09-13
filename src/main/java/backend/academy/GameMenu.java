package backend.academy;

import java.util.Scanner;


public class GameMenu {
    public void startGame() {
        Scanner scanner = new Scanner(System.in);

        int correct_difficulty_choice;
        while (true) {
            System.out.println("Choose the difficulty of mystery word:");
            System.out.println("1 - easy\n2 - medium\n3 - hard");
            String difficulty_choice = scanner.next();
            try {
                correct_difficulty_choice = correctDifficultyChoice(difficulty_choice);
                break;
            } catch (InvalidNumberChoice e) {
                System.out.println(e.message());
            }
        }

        String correct_category_choice;
        while (true) {
            System.out.println("Choose the category of words:");
            System.out.println("animal");
            System.out.println("film");
            System.out.println("country");
            System.out.print("brand\n>");

            try {

                String category_choice = scanner.next();
                correct_category_choice = correctCategoryChoice(category_choice);
                break;

            } catch (InvalidWordException e) {
                System.out.println(e.message());
            }
        }
        GameLogic game_settings = new GameLogic(correct_difficulty_choice, correct_category_choice);
        String answer = game_settings.getWord();

        System.out.println("You can start guessing:");
        if (game_settings.gameProcess(answer, game_settings) > 0) {
            System.out.println("Congratulations, you won! My word was " + answer);
        } else {
            System.out.println("Lucky next time, you lost... My word was " + answer);
        }

        scanner.close();
    }

    public int correctDifficultyChoice(String number_to_check) throws InvalidNumberChoice {
        if (number_to_check.matches(("-?\\d+"))) {
            int difficulty_choice = Integer.parseInt(number_to_check);
            if (difficulty_choice >= 1 && difficulty_choice <= 3) {
                return difficulty_choice;
            }
            else {
                throw new InvalidNumberChoice("Choose the difficulty in range [1, 3]");
            }
        }
        else {
            throw new InvalidNumberChoice("You should input the integer");
        }
    }

    public String correctCategoryChoice(String string_to_check) throws InvalidWordException {
        if (string_to_check.matches("[a-zA-Z]+")) {
            if (string_to_check.equals("animal") || string_to_check.equals("film") ||
            string_to_check.equals("brand") || string_to_check.equals("country")) {
                return string_to_check;
            }
            else {
                throw new InvalidWordException("You should choose one of this suggested categories");
            }
        }
        else {
            throw new InvalidWordException("It can only consists english letters");
        }
    }
}
