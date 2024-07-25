package com.atipera.github.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.atipera.github.api.model.BranchResponse;
import com.atipera.github.api.model.RepositoriesResponse;
import com.atipera.github.api.model.RepositoryResponse;
import com.atipera.github.external.model.Branch;
import com.atipera.github.external.model.Repository;

@Service
public class GithubResponseMapper {

    public RepositoriesResponse mapRepositories(List<Repository> repositories) {
        return RepositoriesResponse.builder()
                .repositories(repositories.stream()
                        .map(this::mapRepository)
                        .collect(Collectors.toList()))
                .build();
    }

    public RepositoryResponse mapRepository(Repository repository) {
        return RepositoryResponse.builder()
                .name(repository.getName())
                .ownerLogin(repository.getOwner().getLogin())
                .build();
    }

    public List<BranchResponse> mapBranches(List<Branch> branches) {
        return branches.stream()
                .map(this::mapBranch)
                .collect(Collectors.toList());
    }

    public BranchResponse mapBranch(Branch branch) {
        return BranchResponse.builder()
                .name(branch.getName())
                .lastCommitSha(branch.getCommit().getSha())
                .build();
    }

}
