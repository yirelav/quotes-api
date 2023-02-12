package com.github.yirelav.quotessolution.web.exception;

import com.github.yirelav.quotessolution.web.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QuoteNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse quoteNotFound(QuoteNotFoundException qnfe) {
        return ErrorResponse.withMessage(qnfe.getMessage());
    }

    @ExceptionHandler(EmptyQuoteListException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse emptyQuoteList(EmptyQuoteListException eqle) {
        return ErrorResponse.withMessage(eqle.getMessage());
    }

    @ExceptionHandler(AuthorAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse authorAlreadyExistsException(AuthorAlreadyExistsException aaee) {
        return ErrorResponse.withMessage(aaee.getMessage());
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse authorNotFoundException(AuthorNotFoundException anfe) {
        return ErrorResponse.withMessage(anfe.getMessage());
    }

}
