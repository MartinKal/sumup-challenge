package sumup.challenge.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sumup.challenge.data.Job;
import sumup.challenge.exceptions.InvalidTaskException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class TaskValidationServiceTest {

    TaskValidationService validationService = new TaskValidationService();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void validTaskShouldSucceed() throws IOException {
        Job job = objectMapper.readValue(
                Files.readString(Path.of("src/test/resources/happy-path/requestbody.json")), Job.class);
        assertDoesNotThrow(() -> validationService.validateJob(job));
    }

    @Test
    public void taskMissingNameThrowsInvalidTaskException() throws IOException {
        Job job = objectMapper.readValue(
                Files.readString(Path.of("src/test/resources/InvalidRequestBody1.json")), Job.class);
        Exception ex = assertThrows(InvalidTaskException.class, () -> {
            validationService.validateJob(job);
        });

        String expectedErrorMessage = "Invalid task data. Provide a name and a command for each task.";
        assertEquals(ex.getMessage(), expectedErrorMessage);
    }

    @Test
    public void taskMissingCommandThrowsInvalidTaskException() throws IOException {
        Job job = objectMapper.readValue(
                Files.readString(Path.of("src/test/resources/InvalidRequestBody2.json")), Job.class);
        Exception ex = assertThrows(InvalidTaskException.class, () -> {
            validationService.validateJob(job);
        });

        String expectedErrorMessage = "Invalid task data. Provide a name and a command for each task.";
        assertEquals(ex.getMessage(), expectedErrorMessage);
    }

    @Test
    public void taskDependingOnNonExistingTaskThrowsInvalidTaskException() throws IOException {
        Job job = objectMapper.readValue(
                Files.readString(Path.of("src/test/resources/InvalidRequestBody3.json")), Job.class);
        Exception ex = assertThrows(InvalidTaskException.class, () -> {
            validationService.validateJob(job);
        });

        String expectedErrorMessage = "Invalid task dependency. task-1 depends on non-existing requirement task-3.";
        assertEquals(ex.getMessage(), expectedErrorMessage);
    }
}
