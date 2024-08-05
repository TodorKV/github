package com.atipera.github.external.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repository {

    private String name;
    private Owner owner;
    @JsonProperty("fork")
    private boolean isFork;
    List<Branch> branches;
}
