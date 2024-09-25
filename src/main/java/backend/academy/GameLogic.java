package backend.academy;

import java.io.PrintStream;
import java.util.ArrayList;
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
            out.println("\nYou right, '" + letter + "' right there");
        } else {
            out.println("\nNice try! " + "there is no '" + letter + "'");
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

        out.print("Your word:");
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
                out.println("\n1 - I ready to guess the whole word");
                out.print("2 - I want to guess the letter\n>");

                try {
                    String currentGuess = scanner.next();
                    correctCurrentGuess = correctChoice(currentGuess);
                    break;

                } catch (InvalidNumberChoice e) {
                    out.println(e.message());
                }

            }

            if (correctCurrentGuess == 1) {
                String correctWordToGuess;
                while (true) {
                    out.print("Input the word\n>");
                    try {
                        String wordToGuess = scanner.next();
                        correctWordToGuess = correctAnswerPrediction(wordToGuess);
                        break;
                    } catch (InvalidWordException e) {
                        out.println(e.message());
                    }
                }
                if (wordCheck(answer, correctWordToGuess)) {
                    return attempts;
                } else {
                    attempts--;
                }
            } else {
                letterCheck(gameSettings, answer, userPredicts, alphabet);
            }
            out.println("Attempts left: " + attempts);
            out.print("Your guests: ");
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
            out.println("Input the letter");
            out.print(">");
            try {
                String prediction = scanner.next();
                correctPrediction = correctLetterPrediction(prediction, alphabet);
                break;
            } catch (InvalidLetterException e) {
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
    public int correctChoice(String numberToCheck) throws InvalidNumberChoice {
        if (numberToCheck.matches(("-?\\d+"))) {
            int currentChoice = Integer.parseInt(numberToCheck);
            if (currentChoice >= 1 && currentChoice <= 2) {
                return currentChoice;
            } else {
                throw new InvalidNumberChoice("Choose the number in range [1, 2]");
            }
        } else {
            throw new InvalidNumberChoice("You should input the integer");
        }
    }

    public String correctAnswerPrediction(String stringToCheck) throws InvalidWordException {
        if (stringToCheck.matches("[а-яА-ЯёЁ]+")) {
            return stringToCheck;
        } else {
            throw new InvalidWordException("It can only consists russian letters");
        }
    }

    public char correctLetterPrediction(String charToCheck, StringBuilder alphabet) throws InvalidLetterException {
        if (charToCheck.length() == 1) {
            char actualCharToCheck = charToCheck.charAt(0);
            if (actualCharToCheck >= 'а' && actualCharToCheck <= 'я' || actualCharToCheck == 'ё'
            || actualCharToCheck >= 'А' && actualCharToCheck <= 'Я' || actualCharToCheck == 'Ё') {
                if (alphabet.indexOf(String.valueOf(actualCharToCheck)) != -1) {
                    return actualCharToCheck;
                } else {
                    throw new InvalidLetterException("Already used");
                }
            } else {
                throw new InvalidLetterException("It can be only russian letter");
            }
        } else {
            throw new InvalidLetterException("You should input only one letter");
        }
    }
}
