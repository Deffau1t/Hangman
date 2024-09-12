package backend.academy;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


@Setter
@Getter
public class gameLogic {
    private int difficulty = 2;
    private String category;
    private int attempts;

    public gameLogic(int difficulty, String category) {
        this.difficulty = difficulty;
        this.category = category;
    }

    public gameLogic() {
    }

    //отбор подходящего списка слов из начального словаря
    public String[] wordList() {
        gameData dict = new gameData();
        switch (this.difficulty) {
            case 3 -> {
                this.attempts = 6;
                return dict.getDictionary(this.category).get("hard");
            }
            case 2 -> {
                this.attempts = 7;
                return dict.getDictionary(this.category).get("medium");
            }
            case 1 -> {
                this.attempts = 7;
                return dict.getDictionary(this.category).get("easy");
            }
        }
        return dict.getDictionary(this.category).get("medium");
    }

    //метод для получения случайного слова из списка по заданным параметрам
    public String getWord() {
        String [] chosen_words = wordList();
        Random random_ind = new Random();
        int index_word = random_ind.nextInt(chosen_words.length);
        return chosen_words[index_word];
    }

    //метод для получения индекса буквы в слове
    public List<Integer> foundLetter(char letter, String answer) {
        answer = answer.toLowerCase();
        letter = Character.toLowerCase(letter);
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < answer.length(); i ++) {
            if (answer.charAt(i) == letter) {
                res.add(i);
            }
        }
        return res;
    }

    public int gameProcess(String answer, gameLogic game_settings) {
        Scanner scanner = new Scanner(System.in);
        char [] user_predicts = new char[answer.length()];
        System.out.print("Your word");
        gameStageIllustration(user_predicts);
        while (true) {
            //gameStageIllustration(user_predicts);
            String current_world = new String(user_predicts);
            if (current_world.equalsIgnoreCase(answer) || game_settings.attempts == 0) {
                break;
            }
            drawing(game_settings);
            System.out.println("1 - I ready to guess the whole word");
            System.out.print("2 - I want to guess the letter\n>");
            int current_guess = scanner.nextInt();
            if (current_guess == 1) {
                System.out.print(">");
                String word_to_guess = scanner.next();
                if (word_to_guess.equalsIgnoreCase(answer)) {
                    return attempts;
                }
            }
            System.out.print(">");
            char prediction = scanner.next().charAt(0);
            List<Integer> ind_list = game_settings.foundLetter(prediction, answer);
            if (!ind_list.isEmpty()) {
                System.out.println("\nYou God dame right '" + prediction + "' right there");
                for (int index : ind_list) {
                    user_predicts[index] = prediction;
                }
            }
            else {
                System.out.println("\nNice try! " + "there is no '" + prediction + "'");
                game_settings.attempts --;
            }
            System.out.println("Attempts left: " + attempts);
            System.out.print("Your guests: ");
            gameStageIllustration(user_predicts);
            System.out.println();
        }
        return attempts;
    }

    public void drawing(gameLogic game_settings) {
        gameData picture = new gameData();
        System.out.println(picture.gameVisualStages(game_settings.attempts, game_settings.difficulty));
    }

//    public void easyPicture(int attempts) {
//        switch (attempts) {
//            case 7 -> {
//
//            }
//        }
//    }

//    public void mediumPicture(int attempts) {
//        return;
//    }

//    public void hardPicture(int attempts) {
//       return;
//    }

    public void gameStageIllustration(char [] user_predicts) {
        System.out.println();
        for (char i : user_predicts) {
            if (i == '\0') {
                System.out.print('_' + " ");
            }
            else {
                System.out.print(i + " ");
            }
        }
        System.out.println("\n");
    }
}
