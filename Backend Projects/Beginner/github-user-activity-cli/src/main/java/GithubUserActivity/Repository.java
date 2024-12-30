package GithubUserActivity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Repository(
        Long id,
        String name,
        String url
) {}
