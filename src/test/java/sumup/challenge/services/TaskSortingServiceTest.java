package sumup.challenge.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sumup.challenge.data.Job;
import sumup.challenge.data.Task;
import sumup.challenge.exceptions.InvalidTaskException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class TaskSortingServiceTest {
    TaskSortingService sortingService = new TaskSortingService();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void sortTasksSuccess() throws IOException {
        Job job = objectMapper.readValue(
                Files.readString(Path.of("src/test/resources/happy-path/requestbody.json")), Job.class);
        List<Task> sortedTasks = sortingService.sortTasks(job.getTasks());
        List<Task> expected = List.of(
                job.getTasks().get(3),
                job.getTasks().get(2),
                job.getTasks().get(1),
                job.getTasks().get(0)
        );
        assertArrayEquals(sortedTasks.toArray(), expected.toArray());
    }

    @Test
    public void sortDetectsCircularDependency() throws IOException {
        Job job = objectMapper.readValue(
                Files.readString(Path.of("src/test/resources/circular-dependency.json")), Job.class
        );

        Exception ex = assertThrows(InvalidTaskException.class, () ->
            sortingService.sortTasks(job.getTasks())
        );

        String expectedMessage = "Found dependency cycle. Task task-1 depends on itself.";
        assertEquals(ex.getMessage(), expectedMessage);
    }

}
