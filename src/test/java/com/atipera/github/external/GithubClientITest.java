package com.atipera.github.external;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.atipera.github.exception.GithubRestException;
import com.atipera.github.external.model.Branch;
import com.atipera.github.external.model.Owner;
import com.atipera.github.external.model.Repository;
import com.github.tomakehurst.wiremock.WireMockServer;

@ActiveProfiles("test")
@SpringBootTest()
@TestInstance(Lifecycle.PER_CLASS)
public class GithubClientITest {

    @Autowired
    private GithubService githubService;
    @Autowired
    private GithubClientMocks githubClientMocks;
    private WireMockServer wireMockServer;

    @BeforeAll
    void init() throws IOException {
        wireMockServer = new WireMockServer(8888);
        wireMockServer.start();
    }

    @AfterAll
    void stopWireMock() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    void getUser_expectNoErrors() throws IOException {
        // given
        githubClientMocks.mockGithubClientGetUserSuccessResponse(wireMockServer);
        String login = "username";

        // when
        Owner owner = githubService.getUser(login);

        // then
        assertTrue(owner != null);
        assertEquals(login, owner.getLogin());
    }

    @Test
    void getUser_expectErrors() throws IOException {
        // given
        githubClientMocks.mockGithubClientGetUserSuccessResponse(wireMockServer);
        String login = "noUser";

        // when then
        assertThrows(GithubRestException.class, () -> githubService.getUser(login));
    }

    @Test
    void getBranches_expectNoErrors() throws IOException {
        // given
        githubClientMocks.mockGithubClientGetBranchesSuccessResponse(wireMockServer);
        // when
        List<Branch> branches = githubService.getRepositoryBranches("username", "repo");

        // then
        assertTrue(branches != null);
        assertTrue(branches.size() > 0);
    }

    @Test
    void getBranches_expectErrors() throws IOException {
        // given
        githubClientMocks.mockGithubClientGetBranchesSuccessResponse(wireMockServer);
        // when then
        assertThrows(GithubRestException.class,
                () -> githubService.getRepositoryBranches("username", "noRepo"));
    }

    @Test
    void getRepos_expectNoErrors() throws IOException {
        // given
        githubClientMocks.mockGithubClientGetReposSuccessResponse(wireMockServer);
        // when
        List<Repository> repositories = githubService.getUserRepositories("username");

        // then
        assertTrue(repositories != null);
        assertTrue(repositories.size() > 0);
    }

    @Test
    void getRepos_expectErrors() throws IOException {
        // given
        githubClientMocks.mockGithubClientGetReposSuccessResponse(wireMockServer);
        // when then
        assertThrows(GithubRestException.class, () -> githubService.getUserRepositories("noUser"));
    }

    @Test
    void getRepos_expectErrorsNotFound() throws IOException {
        // given
        githubClientMocks.mockGithubClientGetReposFailureResponse(wireMockServer);
        // when then
        assertThrows(GithubRestException.class, () -> githubService.getUserRepositories("username"));
    }
}
