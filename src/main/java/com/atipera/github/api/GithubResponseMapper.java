package com.atipera.github.api;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import com.atipera.github.api.model.BranchResponse;
import com.atipera.github.api.model.RepositoriesResponse;
import com.atipera.github.api.model.RepositoryResponse;
import com.atipera.github.external.model.Branch;
import com.atipera.github.external.model.Repository;

@Service
public class GithubResponseMapper {

    public RepositoriesResponse mapRepositories(List<Repository> repositories) {
        return new RepositoriesResponse(repositories.stream()
                .map(this::mapRepository)
                .collect(Collectors.toList()));
    }

    public RepositoryResponse mapRepository(Repository repository) {
        if (ObjectUtils.isNotEmpty(repository.getBranches())) {
            return new RepositoryResponse(
                    repository.getName(),
                    repository.getOwner().getLogin(),
                    mapBranches(repository.getBranches()));
        }
        return new RepositoryResponse(
                repository.getName(),
                repository.getOwner().getLogin(),
                null);
    }

    public List<BranchResponse> mapBranches(List<Branch> branches) {
        return branches.stream()
                .map(this::mapBranch)
                .collect(Collectors.toList());
    }

    public BranchResponse mapBranch(Branch branch) {
        return new BranchResponse(branch.getName(), branch.getCommit().getSha());
    }

}
