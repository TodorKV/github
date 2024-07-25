package com.atipera.github.external;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.atipera.github.external.config.GithubClientConfig;
import com.atipera.github.external.model.Branch;
import com.atipera.github.external.model.Owner;
import com.atipera.github.external.model.Repository;

@FeignClient(name = "githubClient", url = "${github.api.url}", configuration = GithubClientConfig.class)
public interface GithubClient {

        public static final String VND_GITHUB_JSON = "application/vnd.github+json";
        public static final String X_GITHUB_API_VERSION = "2022-11-28";

        @GetMapping(path = "/users/{username}", consumes = VND_GITHUB_JSON)
        Owner getUser(
                        @PathVariable("username") String username,
                        @RequestHeader("Authorization") String authorizationHeader,
                        @RequestHeader("X-GitHub-Api-Version") String apiVersion);

        @GetMapping(path = "/users/{username}/repos", consumes = VND_GITHUB_JSON)
        List<Repository> getUserRepositories(
                        @PathVariable("username") String username,
                        @RequestHeader("Authorization") String authorizationHeader,
                        @RequestHeader("X-GitHub-Api-Version") String apiVersion);

        @GetMapping(path = "/repos/{username}/{repoName}/branches", consumes = VND_GITHUB_JSON)
        List<Branch> getRepositoryBranches(
                        @PathVariable("username") String username,
                        @PathVariable("repoName") String repoName,
                        @RequestHeader("Authorization") String authorizationHeader);
}
