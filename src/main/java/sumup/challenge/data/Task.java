package sumup.challenge.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Task {
    private String name;
    private String command;
    @JsonProperty("requires")
    private List<String> dependencies;

    private Task() {};

    private Task(String name, String command, List<String> dependencies) {
        this.name = name;
        this.command = command;
        this.dependencies = dependencies;
    }

    public String getCommand() {
        return command;
    }

    public String getName() {
        return name;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public static Task of(String name, String command, List<String> dependencies) {
        return new Task(name, command, dependencies);
    }
}
