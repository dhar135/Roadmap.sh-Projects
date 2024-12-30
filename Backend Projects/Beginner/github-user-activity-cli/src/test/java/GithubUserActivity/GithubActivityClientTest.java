package GithubUserActivity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GithubActivityClientTest {

    // System under Test (sut)
    GithubActivityClient client = new GithubActivityClient();

    @BeforeEach
    void setUp() {
        client = new GithubActivityClient();
    }


    @Test
    @DisplayName("Should successfully fetch event for a valid username")
    void shouldFetchUserEventsGivenValidUsername() throws IOException, InterruptedException {
        // When: We fetch events for a valid username
        List<GithubEvent> githubEvents = client.fetchUserEvents("dhar135");

        // Then: The response should not be null and contain events
        assertNotNull(githubEvents, "Github events should not be null");
        assertFalse(githubEvents.isEmpty(), "Github events should not be empty");

        // And: Each event should have valid required fields
        GithubEvent firstEvent = githubEvents.getFirst();
        assertNotNull(firstEvent.id(), "Github event ID should not be null");
        assertNotNull(firstEvent.type(), "Github event type should not be null");
        assertNotNull(firstEvent.actor(), "Github event actor should not be null");
        assertNotNull(firstEvent.repo(), "Github event repo should not be null");

    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException for invalid username")
    void shouldThrowNotFoundExceptionGivenInvalidUsername() throws IOException, InterruptedException {
        // When & Then: The client should throw an exception for an invalid username
        assertThrows(RuntimeException.class, () -> client.fetchUserEvents("dhar1351"), "Should throw UsernameNotFoundException for invalid username");
    }

}