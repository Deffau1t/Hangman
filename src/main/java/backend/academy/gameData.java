package backend.academy;

import java.util.HashMap;
import java.util.Map;

public class gameData {
    private Map<String, String[]> AnimalDictionary = new HashMap<>();
    private Map<String, String[]> FilmDictionary = new HashMap<>();
    private Map<String, String[]> CountryDictionary = new HashMap<>();
    private Map<String, String[]> BrandDictionary = new HashMap<>();

    public gameData() {
        // Animal Dictionary
        AnimalDictionary = new HashMap<>();
        String[] easy_animal_list = new String[] {"корова", "собака", "кошка", "птица", "рыба"};
        AnimalDictionary.put("easy", easy_animal_list);
        String[] medium_animal_list = new String[] {"фламинго", "жираф", "кенгуру", "львёнок", "слон"};
        AnimalDictionary.put("medium", medium_animal_list);
        String[] hard_animal_list = new String[] {"селезень", "бегемот", "носорог", "енот", "кабан"};
        AnimalDictionary.put("hard", hard_animal_list);

        // Film Dictionary
        FilmDictionary = new HashMap<>();
        String[] easy_film_list = new String[] {"фильм", "кино", "актер", "режиссер", "сценарий"};
        FilmDictionary.put("easy", easy_film_list);
        String[] medium_film_list = new String[] {"драма", "комедия", "триллер", "фантастика", "боевик"};
        FilmDictionary.put("medium", medium_film_list);
        String[] hard_film_list = new String[] {"антология", "некромантия", "авантюра", "метафизика", "абсурдизм"};
        FilmDictionary.put("hard", hard_film_list);

        // Country Dictionary
        CountryDictionary = new HashMap<>();
        String[] easy_country_list = new String[] {"Россия", "США", "Китай", "Франция", "Германия"};
        CountryDictionary.put("easy", easy_country_list);
        String[] medium_country_list = new String[] {"Индия", "Бразилия", "Япония", "Канада", "Аргентина"};
        CountryDictionary.put("medium", medium_country_list);
        String[] hard_country_list = new String[] {"Австралия", "Италия", "Мексика", "Испания", "Польша"};
        CountryDictionary.put("hard", hard_country_list);

        // Brand Dictionary
        BrandDictionary = new HashMap<>();
        String[] easy_brand_list = new String[] {"Apple", "Samsung", "Nike", "Coca-Cola", "Adidas"};
        BrandDictionary.put("easy", easy_brand_list);
        String[] medium_brand_list = new String[] {"Toyota", "Google", "Amazon", "Microsoft", "Facebook"};
        BrandDictionary.put("medium", medium_brand_list);
        String[] hard_brand_list = new String[] {"Tesla", "Spotify", "Netflix", "Airbnb", "Uber"};
        BrandDictionary.put("hard", hard_brand_list);
    }

    public Map<String, String[]> getDictionary(String category) {
        switch (category.toLowerCase()) {
            case "animal":
                return AnimalDictionary;
            case "film":
                return FilmDictionary;
            case "country":
                return CountryDictionary;
            case "brand":
                return BrandDictionary;
            default:
                return AnimalDictionary; // Returning default dictionary if category is invalid
        }
    }

    public String gameVisualStages(int attempt, int difficulty) {

        // Массив строк для каждой стадии виселицы
        String[] hangmanStagesEasy = {
            "=========" + "        \n" + "  |   |\n" + "      |\n" + "      |\n" + "      |\n" + "      |\n" + "=========",
            "=========" + "        \n" +
                "  |   |\n" +
                "  O   |\n" +
                "      |\n" +
                "      |\n" +
                "      |\n" +
                "=========",
            "=========" + "       \n" +
                "  |   |\n" +
                "  O   |\n" +
                "  |   |\n" +
                "      |\n" +
                "      |\n" +
                "=========",
            "=========" + "       \n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|   |\n" +
                "      |\n" +
                "      |\n" +
                "=========",
            "=========" + "       \n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                "      |\n" +
                "      |\n" +
                "=========",
            "=========" + "       \n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                " /    |\n" +
                "      |\n" +
                "=========",
            "=========" + "       \n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                " / \\  |\n" +
                "      |\n" +
                "========="
            };
        String[] hangmanStagesHard = {
            "=========" + "       \n" + "  |   |\n" + "      |\n" + "      |\n" + "      |\n" + "      |\n" + "=========",
            "=========" + "       \n" +
                "  |   |\n" +
                "  O   |\n" +
                "  |   |\n" +
                "      |\n" +
                "      |\n" +
                "=========",
            "=========" + "       \n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|   |\n" +
                "      |\n" +
                "      |\n" +
                "=========",
            "=========" + "       \n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                "      |\n" +
                "      |\n" +
                "=========",
            "=========" + "       \n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                " /    |\n" +
                "      |\n" +
                "=========",
            "=========" + "       \n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                " / \\  |\n" +
                "      |\n" +
                "========="
        };
        if (difficulty == 1 || difficulty == 2) {
            return hangmanStagesEasy[hangmanStagesEasy.length - attempt];
        }
        else {
            return hangmanStagesEasy[hangmanStagesHard.length - attempt];
        }
    }
}

