package sumup.challenge.services;

import org.springframework.stereotype.Service;
import sumup.challenge.data.Job;
import sumup.challenge.exceptions.InvalidTaskException;

@Service
public class TaskValidationService {
    public void validateJob(Job job) throws InvalidTaskException {
        job.getTasks().forEach(task -> {
            if (task.getName() == null || task.getCommand() == null) {
                throw new InvalidTaskException("Invalid task data. Provide a name and a command for each task.");
            }
            if (task.getDependencies() != null) {
                task.getDependencies().forEach(dependency -> {
                    if (job.getTasks().stream().noneMatch(t -> t.getName().equals(dependency))) {
                        throw new InvalidTaskException(
                                "Invalid task dependency. " + task.getName() + " depends on non-existing requirement " + dependency + ".");
                    }
                });
            }
        });
    }
}
