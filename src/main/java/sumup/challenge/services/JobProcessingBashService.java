package sumup.challenge.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import sumup.challenge.data.Job;
import sumup.challenge.data.Task;
import sumup.challenge.services.JobProcessingService;

import java.io.IOException;
import java.util.List;

@Service
public class JobProcessingBashService {

    private final JobProcessingService processingService;

    public JobProcessingBashService(JobProcessingService jobProcessingService) {
        processingService = jobProcessingService;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    public List<String> processJob(String json) throws IOException {
        Job job = objectMapper.readValue(json, Job.class);
        Job processedJob = processingService.processJob(job);
        return processedJob.getTasks().stream().map(Task::getCommand).toList();
    }
}
