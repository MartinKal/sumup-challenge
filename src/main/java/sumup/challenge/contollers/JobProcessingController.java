package sumup.challenge.contollers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sumup.challenge.data.Job;
import sumup.challenge.data.TaskResponse;
import sumup.challenge.services.JobProcessingService;

import java.util.List;

@RestController
public class JobProcessingController {
    private final JobProcessingService jobProcessingService;

    public JobProcessingController(JobProcessingService jobProcessingService) {
        this.jobProcessingService = jobProcessingService;
    }

    @PostMapping("processing/jobs")
    public ResponseEntity<List<TaskResponse>> processJob(@RequestBody Job job) {
        Job processedJob = jobProcessingService.processJob(job);
        List<TaskResponse> result = processedJob
                .getTasks()
                .stream()
                .map(item -> TaskResponse.of(item.getName(), item.getCommand()))
                .toList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
