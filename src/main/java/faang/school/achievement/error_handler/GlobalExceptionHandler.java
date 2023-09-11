package faang.school.achievement.error_handler;

import faang.school.achievement.exception.AchievementNotFoundException;
import faang.school.achievement.exception.DataValidationException;
import faang.school.achievement.exception.DeserializeJSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDataValidationException(DataValidationException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(DeserializeJSONException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleDeserializeJSONException(DeserializeJSONException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(AchievementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAchievementNotFoundException(AchievementNotFoundException exception) {
        return exception.getMessage();
    }
}
