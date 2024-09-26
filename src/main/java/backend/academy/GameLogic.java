package backend.academy;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameLogic {
    private int difficulty;
    private String category;
    private int attempts;
    private PrintStream out = System.out;

    public GameLogic(int difficulty, String category) {
        this.difficulty = difficulty;
        this.category = category;
    }

    public GameLogic() {
    }

    //отбор подходящего списка слов из начального словаря
    @SuppressWarnings("MagicNumber")
    public List<String> wordList() {
        GameData dict = new GameData();
        switch (this.difficulty) {
            case 3 -> {
                this.attempts = 6;
                return dict.getDictionary(this.category).get(GameData.DifficultyLevel.HARD);
            }
            case 1 -> {
                this.attempts = 7;
                return dict.getDictionary(this.category).get(GameData.DifficultyLevel.EASY);
            }
            default -> {
                this.attempts = 7;
                return dict.getDictionary(this.category).get(GameData.DifficultyLevel.MEDIUM);
            }
        }
    }

    // метод для получения случайного слова из списка по заданным параметрам
    public String getWord() {
        List<String> chosenWords = wordList();
        Random randomInd = new Random();
        int indexWord = randomInd.nextInt(chosenWords.size());
        return chosenWords.get(indexWord);
    }

    // метод для получения случайной категории
    static String getRandomCategory() {
        List<String> categories = List.of("animal", "film", "country", "brand");
        Random randomInd = new Random();
        int indexCategory = randomInd.nextInt(categories.size());
        return categories.get(indexCategory);
    }

    //метод для получения индекса буквы в слове
    public List<Integer> foundLetter(char letter, String answer) {
        String answerLowerCase = answer.toLowerCase();
        char letterLowerCase = Character.toLowerCase(letter);
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < answerLowerCase.length(); i++) {
            if (answerLowerCase.charAt(i) == letterLowerCase) {
                res.add(i);
            }
        }
        if (!res.isEmpty()) {
            out.println("\nСовершенно верно, '" + letter + "' есть в слове");
        } else {
            out.println("\nНеплохая попытка! " + "в слове нет '" + letter + "'");
            attempts--;
        }
        return res;
    }

    public int gameProcess(String answer, GameLogic gameSettings) {
        Scanner scanner = new Scanner(System.in);

        //массив угаданных пользователем символов
        char[] userPredicts = new char[answer.length()];
        //отображаемый список букв для выбора пользователем
        StringBuilder alphabet = new StringBuilder("абвгдеёжзийклмнопрстуфхцчшщъыьэюя");

        out.print("Ваше слово:");
        gameStageIllustration(userPredicts);

        while (true) {
            String currentWorld = new String(userPredicts);
            //игра заканчивается, когда слово угадано или если попытки закончились
            if (currentWorld.equalsIgnoreCase(answer) || gameSettings.attempts <= 0) {
                break;
            }
            drawing(gameSettings);
            for (int ind = 0; ind < alphabet.length(); ind++) {
                out.print(alphabet.charAt(ind) + " ");
            }

            int correctCurrentGuess;
            while (true) {
                out.print("""
                        \n1 - Я хочу угадать слово целиком
                        2 - Я хочу угадать букву
                        3 - Мне нужна подсказка
                        >""");

                try {
                    String currentGuess = scanner.next();
                    correctCurrentGuess = correctChoice(currentGuess);
                    break;

                } catch (backend.academy.hangmanExceptions.InvalidNumberChoice e) {
                    out.println(e.message());
                }
            }

            if (correctCurrentGuess == 1) {
                String correctWordToGuess;
                while (true) {
                    out.print("Введите загаданное слово\n>");
                    try {
                        String wordToGuess = scanner.next();
                        correctWordToGuess = correctAnswerPrediction(wordToGuess);
                        break;
                    } catch (backend.academy.hangmanExceptions.InvalidWordException e) {
                        out.println(e.message());
                    }
                }
                if (wordCheck(answer, correctWordToGuess)) {
                    return attempts;
                } else {
                    attempts--;
                }
            } else if (correctCurrentGuess == 2) {
                letterCheck(gameSettings, answer, userPredicts, alphabet);
            } else {
                if (difficulty <= 2) {
                    out.println(getHint(category, answer));
                }
                else {
                    out.println("Подсказки не доступны на высокой сложности");
                }
            }
            out.println("Осталось попыток: " + attempts);
            out.print("Угадано: ");
            gameStageIllustration(userPredicts);
            out.println();
        }
        return attempts;
    }

    //отображение виселицы
    public void drawing(GameLogic gameSettings) {
        GameData picture = new GameData();
        out.println(picture.gameVisualStages(gameSettings.attempts, gameSettings.difficulty));
    }

    //отображение угадываемого слова
    public void gameStageIllustration(char[] userPredicts) {
        out.println();
        for (char i : userPredicts) {
            if (i == '\0') {
                out.print('_' + " ");
            } else {
                out.print(i + " ");
            }
        }
        out.println("\n");
    }

    //проверка, что вводимое предположение является ответом
    public boolean wordCheck(String answer, String userWord) {
        if (userWord.equalsIgnoreCase(answer)) {
            return true;
        } else {
            attempts--;
            return false;
        }
    }

    //проверка, что буква есть в слове
    public void letterCheck(GameLogic gameSettings, String answer, char[] userPredicts, StringBuilder alphabet) {
        Scanner scanner = new Scanner(System.in);

        char correctPrediction;
        while (true) {
            out.println("Введите букву");
            out.print(">");
            try {
                String prediction = scanner.next();
                correctPrediction = correctLetterPrediction(prediction, alphabet);
                break;
            } catch (backend.academy.hangmanExceptions.InvalidLetterException e) {
                out.println(e.message());
            }
        }

        List<Integer> indList = gameSettings.foundLetter(correctPrediction, answer);
        if (!indList.isEmpty()) {
            for (int index : indList) {
                userPredicts[index] = correctPrediction;
            }
        }
        alphabet.deleteCharAt(alphabet.indexOf(String.valueOf(correctPrediction)));
    }

    //проверки на ввод во время процесса игры
    @SuppressWarnings("MagicNumbers")
    public int correctChoice(String numberToCheck) throws backend.academy.hangmanExceptions.InvalidNumberChoice {
        if (numberToCheck.matches(("-?\\d+"))) {
            int currentChoice = Integer.parseInt(numberToCheck);
            if (currentChoice >= 1 && currentChoice <= 3) {
                return currentChoice;
            } else {
                throw new backend.academy.hangmanExceptions.InvalidNumberChoice("Введите число в пределах [1, 3]");
            }
        } else {
            throw new backend.academy.hangmanExceptions.InvalidNumberChoice("Нужно ввести число");
        }
    }

    public String correctAnswerPrediction(String stringToCheck) throws backend.academy.hangmanExceptions.InvalidWordException {
        if (stringToCheck.matches("[а-яА-ЯёЁ]+")) {
            return stringToCheck;
        } else {
            throw new backend.academy.hangmanExceptions.InvalidWordException("Слово должно состоять из русских букв");
        }
    }

    public char correctLetterPrediction(String charToCheck, StringBuilder alphabet) throws
        backend.academy.hangmanExceptions.InvalidLetterException {
        if (charToCheck.length() == 1) {
            char actualCharToCheck = charToCheck.toLowerCase().charAt(0);
            if (actualCharToCheck >= 'а' && actualCharToCheck <= 'я' || actualCharToCheck == 'ё') {
                if (alphabet.indexOf(String.valueOf(actualCharToCheck)) != -1) {
                    return actualCharToCheck;
                } else {
                    throw new backend.academy.hangmanExceptions.InvalidLetterException("Уже использовали");
                }
            } else {
                throw new
                    backend.academy.hangmanExceptions.InvalidLetterException("Нужно ввести букву из русского алфавита");
            }
        } else {
            throw new backend.academy.hangmanExceptions.InvalidLetterException("Введите только одну букву");
        }
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
        return animalHintsDictionary.get(animal);
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
        return filmHintsDictionary.get(film);
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
        return countryHintsDictionary.get(country);
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
        return brandHintsDictionary.get(brand);
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
