package sumup.challenge.services;

import org.springframework.stereotype.Service;
import sumup.challenge.exceptions.InvalidTaskException;
import sumup.challenge.data.Job;
import sumup.challenge.data.Task;

import java.io.IOException;
import java.util.*;

@Service
public class JobProcessingService {

    private final TaskValidationService validationService;
    private final TaskSortingService sortingService;
    private final TaskExecutionService taskExecutionService;

    public JobProcessingService(TaskValidationService taskValidationService, TaskSortingService taskSortingService, TaskExecutionService taskExecutionService) {
        validationService = taskValidationService;
        sortingService = taskSortingService;
        this.taskExecutionService = taskExecutionService;
    }

    public Job processJob(Job job) throws InvalidTaskException {
        Job preparedJob = prepareJob(job);
        try {
            taskExecutionService.executeTasks(preparedJob.getTasks());
        } catch (IOException e) {
            System.out.println("An error occurred while executing the command.");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("The process was interrupted.");
            e.printStackTrace();
        }
        return Job.of(preparedJob.getTasks());
    }

    private Job prepareJob(Job job) throws InvalidTaskException {
        validationService.validateJob(job);
        List<Task> sortedTasks = sortingService.sortTasks(job.getTasks());
        return Job.of(sortedTasks);
    }
}
