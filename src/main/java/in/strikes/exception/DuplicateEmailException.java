package in.strikes.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException() {
        super("This email is already registered to another student.");
    }
}
