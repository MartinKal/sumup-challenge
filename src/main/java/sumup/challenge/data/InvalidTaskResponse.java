package sumup.challenge.data;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class InvalidTaskResponse implements ErrorResponse {
    private String message;

    public InvalidTaskResponse(String message) {
        this.message = message;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
