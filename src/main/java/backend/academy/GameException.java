package backend.academy;

import lombok.Getter;

@Getter public class GameException extends Exception {
    private final String message;

    public GameException(String message) {
        this.message = message;
    }
}
