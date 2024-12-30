import GithubUserActivity.GithubActivityClient;
import GithubUserActivity.GithubEvent;

import java.io.IOException;
import java.util.List;

/// Requirements:
///     - Todo: accept the GitHub username as an argument
///     - Todo: fetch the user’s recent activity using the GitHub API
///     - Todo: display it in the terminal


public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 0) {
            System.out.println("Usage: github-user-activity <username>\n");
            return;
        }

        String username = args[0];

        GithubActivityClient githubActivityClient = new GithubActivityClient();
        List<GithubEvent> githubEvents = githubActivityClient.fetchUserEvents(username);

        System.out.println(githubEvents);
    }


}

