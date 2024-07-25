package com.atipera.github.api.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryResponse {

    private String name;
    private String ownerLogin;
    private List<BranchResponse> branches;
}
