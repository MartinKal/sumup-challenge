package sumup.challenge.data;

import java.util.List;

public class Job {
    private Job() {}
    private Job(List<Task> tasks) {
        this.tasks = tasks;
    }
    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    public static Job of(List<Task> tasks) {
        return new Job(tasks);
    }
}
