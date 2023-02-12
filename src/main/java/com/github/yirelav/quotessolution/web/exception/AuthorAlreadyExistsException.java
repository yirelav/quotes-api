package com.github.yirelav.quotessolution.web.exception;

public class AuthorAlreadyExistsException extends RuntimeException {
    public AuthorAlreadyExistsException() {
        super("This name already exists");
    }
}
