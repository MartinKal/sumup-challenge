package sumup.challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sumup.challenge.exceptions.InvalidTaskException;
import sumup.challenge.services.JobProcessingBashService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ChallengeApplication implements CommandLineRunner {
    @Autowired
    JobProcessingBashService processingBashService;

    public static void main(String[] args) {
        SpringApplication.run(ChallengeApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (args.length > 0) {
            try {
                String json = Files.readString(Paths.get(args[0]));
                List<String> tasks = processingBashService.processJob(json);
                tasks.forEach(System.out::println);
                System.exit(0);
            } catch (IOException | InvalidTaskException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
    }
}
