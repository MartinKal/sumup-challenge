package sumup.challenge.services;

import org.springframework.stereotype.Service;
import sumup.challenge.exceptions.InvalidTaskException;
import sumup.challenge.data.Job;
import sumup.challenge.data.Task;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class JobProcessingService {

    private final JobValidationService validationService;
    private final JobSortingService sortingService;

    public JobProcessingService(JobValidationService jobValidationService, JobSortingService jobSortingService) {
        validationService = jobValidationService;
        sortingService = jobSortingService;
    }

    public Job processJob(Job job) throws InvalidTaskException {
        Job preparedJob = prepareJob(job);
        try {
            executeTasks(preparedJob.getTasks());
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

    private void executeTasks(List<Task> tasks) throws InvalidTaskException, InterruptedException, IOException {
        Process process = buildTasks(tasks).start();
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("Process completed successfully.");
        } else {
            System.out.println("Process exited with error code " + exitCode);
        }
        process.destroy();
    }

    private ProcessBuilder buildTasks(List<Task> tasks) {
        ProcessBuilder pb = new ProcessBuilder();
        StringBuilder sb = new StringBuilder();
        tasks.forEach(task ->
                sb.append(task.getCommand()).append(" && ")
        );
        pb.inheritIO();
        pb.directory(new File(System.getProperty("user.dir")));
        pb.command("bash", "-c", sb.substring(0, sb.length() - 4));
        return pb;
    }
}
