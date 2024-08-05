package com.atipera.github.external;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.atipera.github.exception.GithubRestException;
import com.atipera.github.external.model.Branch;
import com.atipera.github.external.model.Owner;
import com.atipera.github.external.model.Repository;

import lombok.RequiredArgsConstructor;

import static com.atipera.github.external.config.GithubClientConfig.X_GITHUB_API_VERSION;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final GithubClient githubClient;
    @Value("${demo.apitera.github.classic-key}")
    private String githubKey;

    public Owner getUser(String username) {
        String token = "Bearer " + githubKey;
        try {
            return githubClient.getUser(username, token, X_GITHUB_API_VERSION);
        } catch (Exception e) {
            throw new GithubRestException("Error fetching github user for username: " + username);
        }
    }

    public List<Repository> getUserRepositories(String username) {
        String token = "Bearer " + githubKey;
        try {
            return githubClient.getUserRepositories(username, token, X_GITHUB_API_VERSION);
        } catch (Exception e) {
            throw new GithubRestException("Error fetching github repositories for username: " + username);
        }
    }

    public List<Repository> getUserRepositoriesWithBranches(String username) {
        String token = "Bearer " + githubKey;
        try {
            List<Repository> repositories = githubClient.getUserRepositories(username, token, X_GITHUB_API_VERSION);
            repositories.forEach(repo -> {
                if (repo.isFork()) {
                    return;
                }
                List<Branch> branches = githubClient.getRepositoryBranches(username, repo.getName(), token);
                repo.setBranches(branches);
            });
            return repositories;

        } catch (Exception e) {
            throw new GithubRestException("Error fetching github repositories for username: " + username);
        }
    }

    public List<Branch> getRepositoryBranches(String username, String repoName) {
        String token = "Bearer " + githubKey;
        try {
            return githubClient.getRepositoryBranches(username, repoName, token);
        } catch (Exception e) {
            throw new GithubRestException(
                    "Error fetching github branches for username: " + username + " and repo: " + repoName);
        }
    }
}
