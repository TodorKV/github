package com.atipera.github.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.atipera.github.api.model.BranchResponse;
import com.atipera.github.api.model.RepositoryResponse;
import com.atipera.github.exception.GithubRestException;
import com.atipera.github.external.GithubService;
import com.atipera.github.external.model.Branch;
import com.atipera.github.external.model.Repository;

@WebMvcTest(GithubController.class)
public class GithubControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GithubService githubService;

    @MockBean
    private GithubResponseMapper githubResponseMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetRepositories() throws Exception {
        // Given
        String username = "testuser";

        Repository repo1 = new Repository();
        repo1.setName("Repo1");
        repo1.setFork(false);

        Repository repo2 = new Repository();
        repo2.setName("Repo2");
        repo2.setFork(true);

        Branch branch1 = new Branch();
        branch1.setName("main");

        BranchResponse branchResponse1 = new BranchResponse();
        branchResponse1.setName("main");

        RepositoryResponse repositoryResponse1 = new RepositoryResponse();
        repositoryResponse1.setName("Repo1");
        repositoryResponse1.setBranches(Arrays.asList(branchResponse1));

        when(githubService.getUserRepositories(username)).thenReturn(Arrays.asList(repo1, repo2));
        when(githubService.getRepositoryBranches(username, "Repo1")).thenReturn(Arrays.asList(branch1));
        when(githubResponseMapper.mapBranches(Arrays.asList(branch1))).thenReturn(Arrays.asList(branchResponse1));
        when(githubResponseMapper.mapRepository(repo1)).thenReturn(repositoryResponse1);

        // When Then
        mockMvc.perform(get("/api/v1/github/repositories/{username}", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.repositories.length()").value(1))
                .andExpect(jsonPath("$.repositories[0].name").value("Repo1"))
                .andExpect(jsonPath("$.repositories[0].branches.length()").value(1))
                .andExpect(jsonPath("$.repositories[0].branches[0].name").value("main"));
    }

    @Test
    public void testGetRepositories_expectErorrs() throws Exception {
        // Given
        String username = "testuser";
        when(githubService.getUserRepositories(username)).thenThrow(new GithubRestException("Error found"));

        // When Then
        mockMvc.perform(get("/api/v1/github/repositories/{username}", username))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Error found"));
    }
}