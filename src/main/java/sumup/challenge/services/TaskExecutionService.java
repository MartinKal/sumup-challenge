package sumup.challenge.services;

import org.springframework.stereotype.Service;
import sumup.challenge.data.Task;
import sumup.challenge.exceptions.InvalidTaskException;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class TaskExecutionService {

    public void executeTasks(List<Task> tasks) throws InvalidTaskException, InterruptedException, IOException {
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
