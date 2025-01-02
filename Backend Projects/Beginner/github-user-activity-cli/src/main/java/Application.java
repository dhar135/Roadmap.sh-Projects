import GithubUserActivity.GithubActivityClient;
import GithubUserActivity.GithubEvent;

import java.io.IOException;
import java.util.List;

public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 0) {
            System.out.println("Usage: github-user-activity <username>\n");
            return;
        }

        String username = args[0];

        GithubActivityClient githubActivityClient = new GithubActivityClient();


        List<GithubEvent> githubEvents = githubActivityClient.fetchUserEvents(username);

        List<String> formatted_events = githubActivityClient.displayUserEvents(githubEvents);

        System.out.println("User Events: ");
        for (String event : formatted_events) {
            System.out.println(event);
        }

    }


}

