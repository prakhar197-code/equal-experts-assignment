package com.example.gist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GistController.class)
class GistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GistService gistService;

    @Test
    void shouldReturnGistsForUser() throws Exception {
        String response = "[{\"id\":\"123\",\"description\":\"test gist\"}]";

        when(gistService.getGists("octocat")).thenReturn(response);

        mockMvc.perform(get("/octocat"))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    void shouldReturnEmptyListWhenUserHasNoGists() throws Exception {
        when(gistService.getGists("octocat")).thenReturn("[]");

        mockMvc.perform(get("/octocat"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        when(gistService.getGists("unknown-user"))
                .thenThrow(new GistService.UserNotFoundException());

        mockMvc.perform(get("/unknown-user"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"error\":\"GitHub user not found\"}"));
    }

    @Test
    void shouldReturnServiceUnavailableWhenGithubIsUnavailable() throws Exception {
        when(gistService.getGists("octocat"))
                .thenThrow(new GistService.GitHubUnavailableException());

        mockMvc.perform(get("/octocat"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(content().json("{\"error\":\"GitHub API is currently unavailable\"}"));
    }
}
