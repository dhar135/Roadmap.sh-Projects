package GithubUserActivity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Actor(
        Integer id,
        String login,
        String display_login,
        String gravatar_id,
        String url,
        String avatar_url
) {}
