package backend.academy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameData {
    private final Map<DifficultyLevel, List<String>> animalDictionary;
    private final Map<DifficultyLevel, List<String>> filmDictionary;
    private final Map<DifficultyLevel, List<String>> countryDictionary;
    private final Map<DifficultyLevel, List<String>> brandDictionary;
    // Части картины для каждой стадии виселицы
    private static final String FLOOR = "=========";
    private static final String INDENT = "       \n";
    private static final String HEAD_INDENT = "  O   |\n";
    private static final String LOW_GROUND_INDENT = "      |\n";
    private static final String HANDS_INDENT = " /|\\  |\n";
    private static final String LEFT_HAND_INDENT = " /|   |\n";
    private static final String MEDIUM_INDENT = "  |   |\n";
    private static final String LEFT_LEG = " /    |\n";
    private static final String LEGS = " / \\  |\n";

    public enum DifficultyLevel {
        EASY,
        MEDIUM,
        HARD
    }

    public GameData() {
        // Animal Dictionary
        animalDictionary = new HashMap<>();
        animalDictionary.put(DifficultyLevel.EASY, List.of("корова", "собака", "кошка", "птица", "рыба"));
        animalDictionary.put(DifficultyLevel.MEDIUM, List.of("фламинго", "жираф", "кенгуру", "львёнок", "слон"));
        animalDictionary.put(DifficultyLevel.HARD, List.of("селезень", "бегемот", "носорог", "енот", "кабан"));

        // Film Dictionary
        filmDictionary = new HashMap<>();
        filmDictionary.put(DifficultyLevel.EASY, List.of("фильм", "кино", "актер", "режиссер", "сценарий"));
        filmDictionary.put(DifficultyLevel.MEDIUM, List.of("драма", "комедия", "триллер", "фантастика", "боевик"));
        filmDictionary.put(DifficultyLevel.HARD, List.of("антология", "некромантия", "авантюра", "метафизика", "абсурдизм"));

        // Country Dictionary
        countryDictionary = new HashMap<>();
        countryDictionary.put(DifficultyLevel.EASY, List.of("Россия", "США", "Китай", "Франция", "Германия"));
        countryDictionary.put(DifficultyLevel.MEDIUM, List.of("Индия", "Бразилия", "Япония", "Канада", "Аргентина"));
        countryDictionary.put(DifficultyLevel.HARD, List.of("Австралия", "Италия", "Мексика", "Испания", "Польша"));

        // Brand Dictionary
        brandDictionary = new HashMap<>();
        brandDictionary.put(DifficultyLevel.EASY, List.of("Яндекс", "Сбер", "Магнит", "Лента", "Роснефть"));
        brandDictionary.put(DifficultyLevel.MEDIUM, List.of("Аэрофлот", "МТС", "Касперский"));
        brandDictionary.put(DifficultyLevel.HARD, List.of("ВКонтакте", "Тинькофф", "Авито"));
    }

    //получение подходящего словаря для игры
    public Map<DifficultyLevel, List<String>> getDictionary(String category) {
        return switch (category.toLowerCase()) {
            case "film" -> filmDictionary;
            case "country" -> countryDictionary;
            case "brand" -> brandDictionary;
            default -> animalDictionary;
        };
    }

    private static String[] createHangmanStages(int difficulty) {
        int size = 0;
        switch (difficulty) {
            case 1, 2 -> size = 7;
            case 3 -> size = 6;
        }
        String[] stages = new String[size];

        stages[0] = buildStage(LOW_GROUND_INDENT.repeat(4));
        stages[1] = buildStage(HEAD_INDENT + LOW_GROUND_INDENT.repeat(3));
        stages[2] = buildStage(HEAD_INDENT + MEDIUM_INDENT + LOW_GROUND_INDENT.repeat(2));
        stages[3] = buildStage(HEAD_INDENT + LEFT_HAND_INDENT + LOW_GROUND_INDENT.repeat(2));
        stages[4] = buildStage(HEAD_INDENT + HANDS_INDENT + LOW_GROUND_INDENT.repeat(2));
        stages[5] = buildStage(HEAD_INDENT + HANDS_INDENT + LEFT_LEG + LOW_GROUND_INDENT);

        if (difficulty == 1) {
            stages[6] = buildStage(HEAD_INDENT + HANDS_INDENT + LEGS + LOW_GROUND_INDENT);
        } else if (difficulty == 2) {
            stages[6] = buildStage(HEAD_INDENT + HANDS_INDENT + LEGS + LOW_GROUND_INDENT);
        }
        return stages;
    }

    static String buildStage(String ground) {
        return FLOOR + INDENT + GameData.MEDIUM_INDENT + ground + FLOOR;
    }

    public String gameVisualStages(int attempt, int difficulty) {
        String[] hangmanStages = createHangmanStages(difficulty);
        return hangmanStages[hangmanStages.length - attempt];
    }
}
