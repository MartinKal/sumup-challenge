package sumup.challenge.data;

import org.springframework.http.HttpStatusCode;

public interface ErrorResponse {
    HttpStatusCode getStatusCode();
    String getMessage();
}
