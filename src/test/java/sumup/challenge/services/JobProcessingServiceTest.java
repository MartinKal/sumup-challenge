package sumup.challenge.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sumup.challenge.data.Job;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class JobProcessingServiceTest {
    @MockBean
    TaskValidationService validationService;

    @MockBean
    TaskSortingService sortingService;

    @MockBean
    TaskExecutionService taskExecutionService;

    JobProcessingService jobProcessingService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void jobProcessingSuccess() throws IOException, InterruptedException {
        jobProcessingService = new JobProcessingService(validationService, sortingService, taskExecutionService);
        Job job = objectMapper.readValue(
                Files.readString(Path.of("src/test/resources/happy-path/requestbody.json")), Job.class);
        Job sortedJob = objectMapper.readValue(
                Files.readString(Path.of("src/test/resources/happy-path/requestbody-sorted.json")), Job.class);


        when(sortingService.sortTasks(job.getTasks())).thenReturn(sortedJob.getTasks());

        jobProcessingService.processJob(job);
        verify(validationService, times(1)).validateJob(job);
        verify(sortingService, times(1)).sortTasks(job.getTasks());
        verify(taskExecutionService, times(1)).executeTasks(sortedJob.getTasks());
    }
}
