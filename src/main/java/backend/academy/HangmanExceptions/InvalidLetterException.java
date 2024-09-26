package backend.academy.hangmanExceptions;

public class InvalidLetterException extends GameException {
    public InvalidLetterException(String message) {
        super(message);
    }
}
