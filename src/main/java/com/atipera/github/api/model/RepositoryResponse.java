package com.atipera.github.api.model;

import java.util.List;

public record RepositoryResponse(String name, String ownerLogin, List<BranchResponse> branches) {}
