package backend.academy.HangmanExceptions;

public class InvalidLetterException extends GameException {
    public InvalidLetterException(String message) {
        super(message);
    }
}
