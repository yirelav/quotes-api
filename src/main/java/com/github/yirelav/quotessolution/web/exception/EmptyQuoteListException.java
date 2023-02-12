package com.github.yirelav.quotessolution.web.exception;

public class EmptyQuoteListException extends RuntimeException {
    public EmptyQuoteListException() {
        super("Quote list is empty");
    }

}
