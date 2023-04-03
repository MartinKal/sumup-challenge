package sumup.challenge.contollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sumup.challenge.data.ErrorResponse;
import sumup.challenge.data.InvalidTaskResponse;
import sumup.challenge.exceptions.InvalidTaskException;

@ControllerAdvice
public class JobProcessingControllerAdvice {

    @ExceptionHandler(value = {InvalidTaskException.class})
    public ResponseEntity<String> handleInvalidTaskException(InvalidTaskException ex) {
        ErrorResponse errorResponse = new InvalidTaskResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse.getMessage(), errorResponse.getStatusCode());
    }
}
