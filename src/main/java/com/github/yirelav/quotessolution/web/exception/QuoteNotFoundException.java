package com.github.yirelav.quotessolution.web.exception;

public class QuoteNotFoundException extends RuntimeException {
    public QuoteNotFoundException(String message) {
        super(message);
    }
    public QuoteNotFoundException(Long quoteId) {
        super("Can't found quote with id " + quoteId);
    }

}
