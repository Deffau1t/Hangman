package backend.academy;

import java.util.HashMap;
import java.util.Map;

public class GameData {
    private Map<String, String[]> AnimalDictionary = new HashMap<>();
    private Map<String, String[]> FilmDictionary = new HashMap<>();
    private Map<String, String[]> CountryDictionary = new HashMap<>();
    private Map<String, String[]> BrandDictionary = new HashMap<>();

    public GameData() {
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
        String[] easy_brand_list = new String[] {"Яндекс", "Сбер", "Магнит", "Лента", "Роснефть"};
        BrandDictionary.put("easy", easy_brand_list);
        String[] medium_brand_list = new String[] {"Аэрофлот", "МТС", "Касперский"};
        BrandDictionary.put("medium", medium_brand_list);
        String[] hard_brand_list = new String[] {"ВКонтакте", "Тинькофф", "Авито"};
        BrandDictionary.put("hard", hard_brand_list);

    }

    public Map<String, String[]> getDictionary(String category) {
        return switch (category.toLowerCase()) {
            case "animal" -> AnimalDictionary;
            case "film" -> FilmDictionary;
            case "country" -> CountryDictionary;
            case "brand" -> BrandDictionary;
            default -> AnimalDictionary;
        };
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

        String[] hangmanStagesMedium = {
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
        if (difficulty == 1) {
            return hangmanStagesEasy[hangmanStagesEasy.length - attempt];
        }
        if (difficulty == 2) {
            return hangmanStagesMedium[hangmanStagesMedium.length - attempt];
        }
        else {
            return hangmanStagesHard[hangmanStagesHard.length - attempt];
        }
    }
}

