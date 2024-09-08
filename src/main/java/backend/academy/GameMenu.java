package backend.academy;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class GameMenu {
    public void start_game() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose the difficulty of mystery word:");
        System.out.println("1 - easy\n2 - medium\n3 - hard");
        int difficulty_chose = scanner.nextInt();

        System.out.println("Choose the category of words:");
        System.out.println("animal");
        String category_chose = scanner.next();

        GameLogic game_settings = new GameLogic(difficulty_chose, category_chose);
        String answer = game_settings.getWord();

        System.out.println("You can start guessing:");
        if (game_process(answer, game_settings) > 0) {
            System.out.println("Congratulations, you won! My word was " + answer);
        }
        else {
            System.out.println("Lucky next time, you lost... My word was " + answer);
        }

    }

    public int game_process(String answer, GameLogic game_settings) {
        Scanner scanner = new Scanner(System.in);
        char [] user_predicts = new char[answer.length()];
        int attempts = game_settings.getAttempts();
        while (true) {
            String current_world = new String(user_predicts);
            if (current_world.equals(answer) || attempts == 0) {
                break;
            }
            System.out.print("You think there is...\n> ");
            char prediction = scanner.next().charAt(0);
            List<Integer> ind_list = game_settings.foundLetter(prediction, answer);
            if (!ind_list.isEmpty()) {
                System.out.println("\nYou God dame right " + prediction + " right there");
                for (int index : ind_list) {
                    user_predicts[index] = prediction;
                }
            }
            else {
                System.out.println("\nNice try! " + "there is no " + prediction);
                attempts --;
            }
            System.out.println("Attempts left: " + attempts);
            System.out.println("Your guests:" + Arrays.toString(user_predicts));
        }
        return attempts;
    }
}
