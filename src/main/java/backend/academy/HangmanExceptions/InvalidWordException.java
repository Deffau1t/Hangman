package backend.academy.HangmanExceptions;

public class InvalidWordException extends GameException {
    public InvalidWordException(String message) {
        super(message);
    }
}
