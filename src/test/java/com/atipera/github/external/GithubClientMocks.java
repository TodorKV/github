package com.atipera.github.external;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;

@Component
public class GithubClientMocks {

    private static final String MOCK_GITHUB_CLIENT_GET_USER_SUCCESS_RESPONSE = "wiremock/github-client-get-user-success-response.json";
    private static final String MOCK_GITHUB_CLIENT_GET_BRANCHES_SUCCESS_RESPONSE = "wiremock/github-client-get-branches-success-response.json";
    private static final String MOCK_GITHUB_CLIENT_GET_REPOS_SUCCESS_RESPONSE = "wiremock/github-client-get-repositories-success-response.json";
    private static final String MOCK_GITHUB_CLIENT_GET_REPOS_FAILURE_RESPONSE = "wiremock/github-client-get-repositories-failure-response.json";

    public void mockGithubClientGetUserSuccessResponse(WireMockServer mockServer) throws IOException {
        stubForGetSuccessResponse("/users/username", mockServer, HttpStatus.OK,
                MOCK_GITHUB_CLIENT_GET_USER_SUCCESS_RESPONSE);
    }

    public void mockGithubClientGetBranchesSuccessResponse(WireMockServer mockServer) throws IOException {
        stubForGetSuccessResponse("/repos/username/repo/branches", mockServer, HttpStatus.OK,
                MOCK_GITHUB_CLIENT_GET_BRANCHES_SUCCESS_RESPONSE);
    }

    public void mockGithubClientGetReposSuccessResponse(WireMockServer mockServer) throws IOException {
        stubForGetSuccessResponse("/users/username/repos", mockServer, HttpStatus.OK,
                MOCK_GITHUB_CLIENT_GET_REPOS_SUCCESS_RESPONSE);
    }

    public void mockGithubClientGetReposFailureResponse(WireMockServer mockServer) throws IOException {
        stubForGetSuccessResponse("/users/username/repos", mockServer, HttpStatus.NOT_FOUND,
                MOCK_GITHUB_CLIENT_GET_REPOS_FAILURE_RESPONSE);
    }

    private void stubForGetSuccessResponse(
            String uri,
            WireMockServer mockServer,
            HttpStatus httpStatus,
            String mockResponsePath) throws IOException {
        final String responseBody = copyToString(
                this.getClass().getClassLoader().getResourceAsStream(mockResponsePath), defaultCharset());
        mockServer.stubFor(buildGetMappingBuilder(uri, httpStatus, responseBody));
    }

    private MappingBuilder buildGetMappingBuilder(String uri, HttpStatus HttpStatus, String responseBody) {
        return WireMock.get(WireMock.urlEqualTo(uri))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody));
    }
}
