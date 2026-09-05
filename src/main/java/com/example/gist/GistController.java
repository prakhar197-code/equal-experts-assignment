package com.example.gist;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GistController {

    private final GistService gistService;

    public GistController(GistService gistService) {
        this.gistService = gistService;
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGists(@PathVariable String username) {
        return ResponseEntity.ok(gistService.getGists(username));
    }
}
