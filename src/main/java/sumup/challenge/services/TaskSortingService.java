package sumup.challenge.services;

import org.springframework.stereotype.Service;
import sumup.challenge.data.Task;
import sumup.challenge.exceptions.InvalidTaskException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskSortingService {
    public List<Task> sortTasks(List<Task> tasks) throws InvalidTaskException {
        List<Task> sortedTasks = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Map<String, Task> tasksMap = tasks
                .stream()
                .collect(Collectors.toMap(Task::getName, task -> task));

        for (Task task : tasks) {
            if (!visited.contains(task.getName())) {
                visit(task, tasksMap, sortedTasks, visited);
            }
        }

        return sortedTasks;
    }

    private void visit(Task task, Map<String, Task> tasks, List<Task> sortedTasks, Set<String> visited) throws InvalidTaskException {
        if (visited.contains(task.getName())) {
            throw new InvalidTaskException("Found dependency cycle. Task " + task.getName() + " depends on itself.");
        }

        visited.add(task.getName());

        if (task.getDependencies() != null) {
            for (String dependency : task.getDependencies()) {
                Task dependencyTask = tasks.get(dependency);
                visit(dependencyTask, tasks, sortedTasks, visited);
            }
        }

        sortedTasks.add(task);
    }

}
