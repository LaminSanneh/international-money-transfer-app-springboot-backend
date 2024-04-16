package internationalmoneytransferapp.Backend.services.exceptions;

public class CannotCreateTransactionException extends RuntimeException {

    public CannotCreateTransactionException(String message) {
        super(message);
    }
}
