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
        filmDictionary.put(DifficultyLevel.HARD, List.of("антология", "некромантия", "авантюра", "метафизика"));

        // Country Dictionary
        countryDictionary = new HashMap<>();
        countryDictionary.put(DifficultyLevel.EASY, List.of("Россия", "США", "Китай", "Франция", "Германия"));
        countryDictionary.put(DifficultyLevel.MEDIUM, List.of("Австралия", "Италия", "Мексика", "Испания", "Польша"));
        countryDictionary.put(DifficultyLevel.HARD, List.of("Индия", "Бразилия", "Япония", "Канада", "Аргентина"));

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

    @SuppressWarnings("MagicNumber")
    private static String[] createHangmanStages(int difficulty) {
        int size;
        if (difficulty == 1) {
            size = 7;
        } else if (difficulty == 2) {
            size = 7;
        } else {
            size = 6;
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

    public static String getAnimalHint(String animal) {
        HashMap<String, String> animalHintsDictionary = new HashMap<>() {{
        put("корова", "домашнее животное, дающее молоко");
        put("собака", "верный друг человека");
        put("кошка", "популярное домашнее животное");
        put("птица", "может летать");
        put("рыба", "водное животное");
        put("фламинго", "розовая птица с длинными ногами");
        put("жираф", "самое высокое животное на планете");
        put("кенгуру", "австралийское животное с мешком");
        put("львёнок", "молодой лев");
        put("слон", "крупное наземное млекопитающее");
        }};
        return animalHintsDictionary.getOrDefault(animal, "There are no hints for hard difficulty");
    }

    public static String getFilmHint(String film) {
        HashMap<String, String> filmHintsDictionary = new HashMap<>() {{
            put("фильм", "визуальное искусство");
            put("кино", "место показа фильмов");
            put("актер", "человек, играющий роль в фильме");
            put("режиссер", "человек, который управляет процессом съемки");
            put("сценарий", "текст для фильма");
            put("драма", "жанр для слёз");
            put("комедия", "жанр для смеха");
            put("триллер", "жанр с напряжением и интригой");
            put("фантастика", "жанр о вымышленных мирах");
            put("боевик", "жанр с экшен-сценами");
        }};
        return filmHintsDictionary.getOrDefault(film, "There are no hints for hard difficulty");
    }

    public static String getCountryHint(String country) {
        HashMap<String, String> countryHintsDictionary = new HashMap<>() {{
            put("Россия", "самая большая страна в мире");
            put("США", "страна с 50 штатами");
            put("Китай", "страна с самой большой численностью населения");
            put("Франция", "страна с Эйфелевой башней");
            put("Германия", "страна с богатой историей и культурой");
            put("Австралия", "континент и страна одновременно");
            put("Италия", "страна пасты и пиццы");
            put("Мексика", "страна с богатой историей майя");
            put("Испания", "страна фламенко и корриды");
            put("Польша", "страна в Центральной Европе");
        }};
        return countryHintsDictionary.getOrDefault(country, "There are no hints for hard difficulty");
    }

    public static String getBrandHint(String brand) {
        HashMap<String, String> brandHintsDictionary = new HashMap<>() {{
            put("Яндекс", "поисковая система из России");
            put("Сбер", "крупнейший банк в России");
            put("Магнит", "сеть супермаркетов");
            put("Лента", "торговая сеть гипермаркетов");
            put("Роснефть", "крупная нефтяная компания");
            put("Аэрофлот", "крупнейшая авиакомпания России");
            put("МТС", "один из крупных операторов связи");
            put("Касперский", "известная антивирусная компания");
        }};
        return brandHintsDictionary.getOrDefault(brand, "There are no hints for hard difficulty");
    }

    static String getHint(String category, String answer) {
        return switch (category.toLowerCase()) {
            case "film" -> getFilmHint(answer);
            case "country" -> getCountryHint(answer);
            case "brand" -> getBrandHint(answer);
            default -> getAnimalHint(answer);
        };
    }
}
