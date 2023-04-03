package sumup.challenge.data;

public class TaskResponse {
    private String name;
    private String command;

    private TaskResponse() {}

    private TaskResponse(String name, String command) {
        this.name = name;
        this.command = command;
    }

    public static TaskResponse of(String name, String command) {
        return new TaskResponse(name, command);
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }
}
