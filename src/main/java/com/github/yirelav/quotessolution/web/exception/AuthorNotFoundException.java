package com.github.yirelav.quotessolution.web.exception;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(String author) {
        super("Can't find an author named " + author);
    }
}
