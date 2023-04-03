package sumup.challenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sumup.challenge.contollers.JobProcessingController;
import sumup.challenge.data.Job;
import sumup.challenge.exceptions.InvalidTaskException;
import sumup.challenge.services.JobProcessingBashService;
import sumup.challenge.services.JobProcessingService;
import sumup.challenge.services.TaskSortingService;
import sumup.challenge.services.TaskValidationService;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JobProcessingController.class)
public class JobProcessingControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    JobProcessingService processingService;

    @MockBean
    TaskSortingService sortingService;

    @MockBean
    TaskValidationService validationService;

    @MockBean
    JobProcessingBashService bashService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void jobProcessingHappyPath() throws Exception {
        String requestBody = Files.readString(Path.of("src/test/resources/happy-path/requestbody.json"));
        Job job = objectMapper.readValue(requestBody, Job.class);
        String response = Files.readString(Path.of("src/test/resources/happy-path/response.json"));
        Mockito.when(processingService.processJob(any()))
                .thenReturn(Job.of(job.getTasks()));
        mockMvc.perform(MockMvcRequestBuilders.post("/processing/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }
}
