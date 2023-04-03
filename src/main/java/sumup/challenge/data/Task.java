package sumup.challenge.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Task {
    private String name;
    private String command;
    @JsonProperty("requires")
    private List<String> dependencies;

    public String getCommand() {
        return command;
    }

    public String getName() {
        return name;
    }

    public List<String> getDependencies() {
        return dependencies;
    }
}
