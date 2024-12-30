package GithubUserActivity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubEvent(
        String id,
        String type,
        Actor actor,
        Repository repo,
        JsonNode payload,
        boolean isPublic,
        @JsonProperty("created_at")
        String createdAt
) {}

