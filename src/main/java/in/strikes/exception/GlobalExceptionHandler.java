package in.strikes.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(StudentNotFoundException ex, Model model) {
        model.addAttribute("title", "Student not found");
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleDatabase(DataAccessException ex, Model model) {
        model.addAttribute("title", "Database problem");
        model.addAttribute("message",
                "The request could not be completed. Check that PostgreSQL is running and that "
                        + "the credentials in application.properties are correct.");
        return "error";
    }
}
