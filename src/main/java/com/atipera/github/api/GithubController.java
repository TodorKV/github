package com.atipera.github.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atipera.github.api.model.BranchResponse;
import com.atipera.github.api.model.RepositoriesResponse;
import com.atipera.github.api.model.RepositoryResponse;
import com.atipera.github.external.GithubService;
import com.atipera.github.external.model.Branch;
import com.atipera.github.external.model.Repository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/github")
@RequiredArgsConstructor
public class GithubController {

    private final GithubService githubService;
    private final GithubResponseMapper githubResponseMapper;

    @GetMapping(path = "/repositories/{username}")
    public ResponseEntity<RepositoriesResponse> getRepositories(@PathVariable(name = "username") String username) {
        List<Repository> repos = githubService.getUserRepositoriesWithBranches(username);
        return ResponseEntity.ok(githubResponseMapper.mapRepositories(repos));

    }
}
