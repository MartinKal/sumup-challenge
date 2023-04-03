# sumup-challenge

The main method of the app is in ChallengeApplication.class

When started the application is listening to the following REST endpoint:
http://localhost:8080/processing/jobs POST
expects json requestBody like this one:
```json
{
    "tasks": [
        {
            "name": "task-1",
            "command": "touch file1",
            "requires": ["task-3", "task-2"]
        },
        {
            "name": "task-4",
            "command": "echo 'Hello World!'"
        },
        {
            "name": "task-2",
            "command": "echo 'Hello World!'"
        },
        {
            "name": "task-3",
            "command": "ls",
            "requires": ["task-4"]
        }
    ]
}
```
it validates, sorts in the correct order and executes the commands. The app responds with the sorted list of tasks:
```json
[
    {
        "name": "task-4",
        "command": "echo 'Hello World!'"
    },
    {
        "name": "task-3",
        "command": "ls"
    },
    {
        "name": "task-2",
        "command": "echo 'Hello World!'"
    },
    {
        "name": "task-1",
        "command": "touch file1"
    }
]
```

A json file containing the tasks can also be provided to the app using the command line. The app executes the tasks, returns the tasks sorted and terminates.
