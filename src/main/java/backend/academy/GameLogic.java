package backend.academy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogic {
    private int difficulty = 2;
    private String category;
    private int attempts;
    private String word;

    public GameLogic(int difficulty, String category) {
        this.difficulty = difficulty;
        this.category = category;
    }

    public GameLogic() {
    }

    public String getCategory() {
        return this.category;
    }

    public int getAttempts() {
        return this.attempts;
    }

    //отбор подходящего списка слов из начального словаря
    public String[] word_list() {
        GameData dict = new GameData();
        switch (this.difficulty) {
            case 3 -> {
                this.attempts = 3;
                return dict.getDictionary(this.category).get("hard");
            }
            case 2 -> {
                this.attempts = 4;
                return dict.getDictionary(this.category).get("medium");
            }
            case 1 -> {
                this.attempts = 5;
                return dict.getDictionary(this.category).get("easy");
            }
        }
        return dict.getDictionary(this.category).get("medium");
    }

    //метод для получения случайного слова из списка по заданным параметрам
    public String getWord() {
        String [] chosen_words = word_list();
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

}
