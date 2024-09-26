package backend.academy.hangmanExceptions;

public class InvalidWordException extends GameException {
    public InvalidWordException(String message) {
        super(message);
    }
}
