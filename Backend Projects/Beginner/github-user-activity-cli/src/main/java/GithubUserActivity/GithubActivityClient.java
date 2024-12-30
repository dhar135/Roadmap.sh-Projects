package GithubUserActivity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class GithubActivityClient {

    private final String BASE_URL = "https://api.github.com/users/%s/events";
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public GithubActivityClient() {
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
    }


    public List<GithubEvent> fetchUserEvents(String username) throws IOException, InterruptedException {

        String url = String.format(BASE_URL, username);


        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/vnd.github.v3+json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return switch (response.statusCode()) {
                case 200 -> {
                    if (response.body() == null) {
                        throw new RuntimeException("Response body is null");
                    }

                    if (response.body().isEmpty()) {
                        throw new RuntimeException("Response body is empty");
                    }

                    yield objectMapper.readValue(response.body(), new TypeReference<>() {
                    });
                }
                case 404 -> throw new RuntimeException("User not found");
                case 403 -> throw new RuntimeException("Rate limit exceeded or access denied");
                default -> throw new RuntimeException("Unexpected status code " + response.statusCode());
            };

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to fetch user events", e);
        }
    }

    public void displayUserEvents(List<GithubEvent> events) throws IOException, InterruptedException {

    }


}
