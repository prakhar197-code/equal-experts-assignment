package com.example.gist;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(GistService.UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> userNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "GitHub user not found"));
    }

    @ExceptionHandler(GistService.GitHubRateLimitException.class)
    public ResponseEntity<Map<String, String>> rateLimited() {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(Map.of("error", "GitHub API rate limit reached"));
    }

    @ExceptionHandler(GistService.GitHubUnavailableException.class)
    public ResponseEntity<Map<String, String>> unavailable() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("error", "GitHub API is currently unavailable"));
    }

    @ExceptionHandler(GistService.GitHubException.class)
    public ResponseEntity<Map<String, String>> githubError() {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("error", "Unable to get data from GitHub"));
    }
}
