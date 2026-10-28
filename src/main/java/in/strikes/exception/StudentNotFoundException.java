package in.strikes.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(Long id) {
        super("No student exists with id " + id + ". They may have been deleted.");
    }
}
