package backend.academy;

import java.util.HashMap;
import java.util.Map;

public class GameData {
    private final Map<String, String[]> animalDictionary;
    private final Map<String, String[]> filmDictionary;
    private final Map<String, String[]> countryDictionary;
    private final Map<String, String[]> brandDictionary;

    public GameData() {
        String easyLevel = "easy";
        String mediumLevel = "medium";
        String hardLevel = "hard";
        // Animal Dictionary
        animalDictionary = new HashMap<>();
        String[] easyAnimalList = new String[] {"корова", "собака", "кошка", "птица", "рыба"};
        animalDictionary.put(easyLevel, easyAnimalList);
        String[] mediumAnimalList = new String[] {"фламинго", "жираф", "кенгуру", "львёнок", "слон"};
        animalDictionary.put(mediumLevel, mediumAnimalList);
        String[] hardAnimalList = new String[] {"селезень", "бегемот", "носорог", "енот", "кабан"};
        animalDictionary.put(hardLevel, hardAnimalList);

        // Film Dictionary
        filmDictionary = new HashMap<>();
        String[] easyFilmList = new String[] {"фильм", "кино", "актер", "режиссер", "сценарий"};
        filmDictionary.put(easyLevel, easyFilmList);
        String[] mediumFilmList = new String[] {"драма", "комедия", "триллер", "фантастика", "боевик"};
        filmDictionary.put(mediumLevel, mediumFilmList);
        String[] hardFilmList = new String[] {"антология", "некромантия", "авантюра", "метафизика", "абсурдизм"};
        filmDictionary.put(hardLevel, hardFilmList);

        // Country Dictionary
        countryDictionary = new HashMap<>();
        String[] easyCountryList = new String[] {"Россия", "США", "Китай", "Франция", "Германия"};
        countryDictionary.put(easyLevel, easyCountryList);
        String[] mediumCountryList = new String[] {"Индия", "Бразилия", "Япония", "Канада", "Аргентина"};
        countryDictionary.put(mediumLevel, mediumCountryList);
        String[] hardCountryList = new String[] {"Австралия", "Италия", "Мексика", "Испания", "Польша"};
        countryDictionary.put(hardLevel, hardCountryList);

        // Brand Dictionary
        brandDictionary = new HashMap<>();
        String[] easyBrandList = new String[] {"Яндекс", "Сбер", "Магнит", "Лента", "Роснефть"};
        brandDictionary.put(easyLevel, easyBrandList);
        String[] mediumBrandList = new String[] {"Аэрофлот", "МТС", "Касперский"};
        brandDictionary.put(mediumLevel, mediumBrandList);
        String[] hardBrandList = new String[] {"ВКонтакте", "Тинькофф", "Авито"};
        brandDictionary.put(hardLevel, hardBrandList);

    }

    //получение подходящего словаря для игры
    public Map<String, String[]> getDictionary(String category) {
        return switch (category.toLowerCase()) {
            case "film" -> filmDictionary;
            case "country" -> countryDictionary;
            case "brand" -> brandDictionary;
            default -> animalDictionary;
        };
    }

    public String gameVisualStages(int attempt, int difficulty) {

        // Части картины для каждой стадии виселицы
        String floor = "=========";
        String indent = "       \n";
        //String stageIndent = "  |   |\n";
        String headIndent = "  O   |\n";
        String lowGroundIndent = "      |\n";
        String handsIndent = " /|\\  |\n";
        String leftHandIndent = " /|   |\n";
        String mediumIndent = "  |   |\n";
        String leftLeg = " /    |\n";
        String legs = " / \\  |\n";

        String firstStage = floor
                + indent
                + mediumIndent
                + lowGroundIndent
                + lowGroundIndent
                + lowGroundIndent
                + lowGroundIndent
                + floor;

        String easyAdditionalStage = floor
                + indent
                + mediumIndent
                + headIndent
                + lowGroundIndent
                + lowGroundIndent
                + lowGroundIndent
                + floor;

        String secondStage = floor
                + indent
                + mediumIndent
                + headIndent
                + mediumIndent
                + lowGroundIndent
                + lowGroundIndent
                + floor;

        String thirdStage = floor
                + indent
                + mediumIndent
                + headIndent
                + leftHandIndent
                + lowGroundIndent
                + lowGroundIndent
                + floor;

        String fourthStage = floor
                + indent
                + mediumIndent
                + headIndent
                + handsIndent
                + lowGroundIndent
                + lowGroundIndent
                + floor;

        String fifthStage = floor
                + indent
                + mediumIndent
                + headIndent
                + handsIndent
                + leftLeg
                + lowGroundIndent
                + floor;

        String sixthStage = floor
                + indent
                + mediumIndent
                + headIndent
                + handsIndent
                + legs
                + lowGroundIndent
                + floor;

        String[] hangmanStagesEasy = {
            firstStage,
            easyAdditionalStage,
            secondStage,
            thirdStage,
            fourthStage,
            fifthStage,
            sixthStage
            };

        String[] hangmanStagesHard = {
            firstStage,
            secondStage,
            thirdStage,
            fourthStage,
            fifthStage,
            sixthStage
            };

        if (difficulty == 1) {
            return hangmanStagesEasy[hangmanStagesEasy.length - attempt];
        }
        if (difficulty == 2) {
            return hangmanStagesEasy[hangmanStagesEasy.length - attempt];
        } else {
            return hangmanStagesHard[hangmanStagesHard.length - attempt];
        }
    }
}

