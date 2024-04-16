package internationalmoneytransferapp.Backend.services.exceptions;

public class CannotCreateSavedBeneficiaryException extends RuntimeException {

    public CannotCreateSavedBeneficiaryException(String message) {
        super(message);
    }
}
