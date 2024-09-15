package backend.academy;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


@Setter
@Getter
public class GameLogic {
    private int difficulty = 2;
    private String category = "animal";
    private int attempts;

    public GameLogic(int difficulty, String category) {
        this.difficulty = difficulty;
        this.category = category;
    }

    public GameLogic() {
    }

    //отбор подходящего списка слов из начального словаря
    public String[] wordList() {
        GameData dict = new GameData();
        switch (this.difficulty) {
            case 3 -> {
                this.attempts = 6;
                return dict.getDictionary(this.category).get("hard");
            }
            case 2 -> {
                this.attempts = 7;
                return dict.getDictionary(this.category).get("medium");
            }
            case 1 -> {
                this.attempts = 7;
                return dict.getDictionary(this.category).get("easy");
            }
        }
        return dict.getDictionary(this.category).get("medium");
    }

    //метод для получения случайного слова из списка по заданным параметрам
    public String getWord() {
        String [] chosen_words = wordList();
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

    public int gameProcess(String answer, GameLogic game_settings) {
        Scanner scanner = new Scanner(System.in);

        //массив угаданных пользователем символов
        char [] user_predicts = new char[answer.length()];
        //отображаемый список букв для выбора пользователем
        StringBuilder alphabet = new StringBuilder("абвгдеёжзийклмнопрстуфхцчшщъыьэюя");

        System.out.print("Your word:");
        gameStageIllustration(user_predicts);

        while (true) {
            String current_world = new String(user_predicts);
            //игра заканчивается, когда слово угадано или если попытки закончились
            if (current_world.equalsIgnoreCase(answer) || game_settings.attempts == 0) {
                break;
            }
            drawing(game_settings);
            for (int ind = 0; ind < alphabet.length(); ind++) {
                System.out.print(alphabet.charAt(ind) + " ");
            }

            int correct_current_guess;
            while (true) {
                System.out.println("\n1 - I ready to guess the whole word");
                System.out.print("2 - I want to guess the letter\n>");

                try {
                    String current_guess = scanner.next();
                    correct_current_guess = correctChoice(current_guess);
                    break;

                } catch (InvalidNumberChoice e) {
                    System.out.println(e.message());
                }

            }
            if (correct_current_guess == 1) {
                if (wordCheck(answer)) {
                    return attempts;
                }
                else {
                    attempts --;
                }
            } else {
                letterCheck(game_settings, answer, user_predicts, alphabet);
            }
            System.out.println("Attempts left: " + attempts);
            System.out.print("Your guests: ");
            gameStageIllustration(user_predicts);
            System.out.println();
        }
        return attempts;
    }

    //отображение виселицы
    public void drawing(GameLogic game_settings) {
        GameData picture = new GameData();
        System.out.println(picture.gameVisualStages(game_settings.attempts, game_settings.difficulty));
    }

    //отображение угадываемого слова
    public void gameStageIllustration(char [] user_predicts) {
        System.out.println();
        for (char i : user_predicts) {
            if (i == '\0') {
                System.out.print('_' + " ");
            }
            else {
                System.out.print(i + " ");
            }
        }
        System.out.println("\n");
    }

    //проверка, что вводимое предположение является ответом
    public boolean wordCheck(String answer) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Input the word");
            System.out.print(">");
            try {
                String word_to_guess = scanner.next();
                String correct_word_to_guess = correctAnswerPrediction(word_to_guess);
                return correct_word_to_guess.equalsIgnoreCase(answer);
            } catch (InvalidWordException e) {
                System.out.println(e.message());
            }
        }
    }

    //проверка, что буква есть в слове
    public void letterCheck(GameLogic game_settings, String answer, char [] user_predicts, StringBuilder alphabet) {
        Scanner scanner = new Scanner(System.in);

        char correct_prediction;
        while (true) {
            System.out.println("Input the letter");
            System.out.print(">");
            try {
                String prediction = scanner.next();
                correct_prediction = correctLetterPrediction(prediction, alphabet);
                break;
            } catch (InvalidLetterException e) {
                System.out.println(e.message());
            }
        }

        List<Integer> ind_list = game_settings.foundLetter(correct_prediction, answer);
        if (!ind_list.isEmpty()) {
            System.out.println("\nYou right, '" + correct_prediction + "' right there");
            for (int index : ind_list) {
                user_predicts[index] = correct_prediction;
            }
        } else {
            System.out.println("\nNice try! " + "there is no '" + correct_prediction + "'");
            game_settings.attempts--;
        }
        alphabet.deleteCharAt(alphabet.indexOf(String.valueOf(correct_prediction)));
    }

    //проверки на ввод во время процесса игры
    public int correctChoice(String number_to_check) throws InvalidNumberChoice {
        if (number_to_check.matches(("-?\\d+"))) {
            int current_choice = Integer.parseInt(number_to_check);
            if (current_choice >= 1 && current_choice <= 2) {
                return current_choice;
            }
            else {
                throw new InvalidNumberChoice("Choose the number in range [1, 2]");
            }
        }
        else {
            throw new InvalidNumberChoice("You should input the integer");
        }
    }

    public String correctAnswerPrediction(String string_to_check) throws InvalidWordException {
        if (string_to_check.matches("[а-яА-ЯёЁ]+")) {
            return string_to_check;
        }
        else {
            throw new InvalidWordException("It can only consists russian letters");
        }
    }

    public char correctLetterPrediction(String char_to_check, StringBuilder alphabet) throws InvalidLetterException {
        if (char_to_check.length() == 1) {
            char actual_char_to_check = char_to_check.charAt(0);
            if (actual_char_to_check >= 'а' && actual_char_to_check <= 'я' || actual_char_to_check == 'ё'
            || actual_char_to_check >= 'А' && actual_char_to_check <= 'Я' || actual_char_to_check == 'Ё') {
                if (alphabet.indexOf(String.valueOf(actual_char_to_check)) != -1) {
                    return actual_char_to_check;
                }
                else {
                    throw new InvalidLetterException("Already used");
                }
            }
            else {
                throw new InvalidLetterException("It can be only russian letter");
            }
        }
        else {
            throw new InvalidLetterException("You should input only one letter");
        }
    }
}
