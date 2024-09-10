package backend.academy;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class gameMenu {
    public void start_game() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose the difficulty of mystery word:");
        System.out.println("1 - easy\n2 - medium\n3 - hard");
        int difficulty_chose = scanner.nextInt();

        System.out.println("Choose the category of words:");
        System.out.println("animal");
        System.out.println("film");
        System.out.println("country");
        System.out.print("brand\n>");
        String category_chose = scanner.next();

        gameLogic game_settings = new gameLogic(difficulty_chose, category_chose);
        String answer = game_settings.getWord();

        System.out.println("You can start guessing:");
        if (game_settings.game_process(answer, game_settings) > 0) {
            System.out.println("Congratulations, you won! My word was " + answer);
        }
        else {
            System.out.println("Lucky next time, you lost... My word was " + answer);
        }

    }
}
