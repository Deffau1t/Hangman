package backend.academy;

import java.util.Random;

public class GameLogic {
    private String difficulty = "medium";
    private String category;
    private int attempts;
    private String word;

    public GameLogic(String difficulty, String category) {
        this.difficulty = difficulty;
        this.category = category;
    }

    public GameLogic() {
    }

    public String getCategory() {
        return this.category;
    }

    public String[] Word_list() {
        GameData dict = new GameData();
        if (this.difficulty.equals("hard")) {
            this.attempts = 3;
            return dict.getDictionary(this.category).get("hard");
        }
        if (this.difficulty.equals("medium")) {
            this.attempts = 4;
            return dict.getDictionary(this.category).get("medium");
        }
        if (this.difficulty.equals("easy")) {
            this.attempts = 5;
            return dict.getDictionary(this.category).get("easy");
        }
        return dict.getDictionary(this.category).get("medium");
    }

    public String GetWord() {
        String [] chosen_words = Word_list();
        Random random_ind = new Random();
        int index_word = random_ind.nextInt(chosen_words.length);
        return chosen_words[index_word];
    }

}
