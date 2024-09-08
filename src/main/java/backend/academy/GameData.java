package backend.academy;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class GameData {
    private Map<String, String []> AnimalDictionary = new HashMap<>();

    public GameData() {
        AnimalDictionary = new HashMap<>();
        String [] easy_word_list = new String[] {"корова"};
        AnimalDictionary.put("easy", easy_word_list);
        String [] medium_word_list = new String[] {"фламинго"};
        AnimalDictionary.put("medium", medium_word_list);
        String [] hard_word_list = new String[] {"селезень"};
        AnimalDictionary.put("hard", hard_word_list);
    }

    public Map<String, String[]> getDictionary(String category) {
        if (category.equals("animal")) {
            return AnimalDictionary;
        }
        return AnimalDictionary;
    }

}
