package harmony;

public class InvalidNameException extends Exception {
    public InvalidNameException() {}

    public InvalidNameException(String message) {
        super(message);
    }
}
