package com.atipera.github.exception;

public class GithubRestException extends RuntimeException {
    public GithubRestException(String message) {
        super(message);
    }
}
