package com.example.gist;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Service
public class GistService {

    private final RestClient client;

    public GistService(RestClient.Builder builder) {
        this.client = builder
                .baseUrl("https://api.github.com")
                .build();
    }

    public String getGists(String username) {
        try {
            return client.get()
                    .uri("/users/{username}/gists", username)
                    .header("Accept", "application/vnd.github+json")
                    .header("X-GitHub-Api-Version", "2026-03-10")
                    .retrieve()
                    .body(String.class);
        } catch (HttpStatusCodeException ex) {
            if (ex.getStatusCode().value() == 404) {
                throw new UserNotFoundException();
            }

            if (ex.getStatusCode().value() == 403 || ex.getStatusCode().value() == 429) {
                throw new GitHubRateLimitException();
            }

            throw new GitHubException();
        } catch (ResourceAccessException ex) {
            throw new GitHubUnavailableException();
        }
    }

    static class UserNotFoundException extends RuntimeException {
    }

    static class GitHubRateLimitException extends RuntimeException {
    }

    static class GitHubUnavailableException extends RuntimeException {
    }

    static class GitHubException extends RuntimeException {
    }
}
