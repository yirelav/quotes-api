package com.github.yirelav.quotessolution.web.controller;

import com.github.yirelav.quotessolution.domain.entities.Quote;
import com.github.yirelav.quotessolution.service.QuoteService;
import com.github.yirelav.quotessolution.web.converter.QuoteConverter;
import com.github.yirelav.quotessolution.web.dto.CreateQuoteRequest;
import com.github.yirelav.quotessolution.web.dto.CreateQuoteResponse;
import com.github.yirelav.quotessolution.web.dto.QuoteResponse;
import com.github.yirelav.quotessolution.web.dto.UpdateQuoteResponse;
import com.github.yirelav.quotessolution.web.exception.EmptyQuoteListException;
import com.github.yirelav.quotessolution.web.exception.QuoteNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quotes")
@AllArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;
    private final QuoteConverter converter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateQuoteResponse add(@RequestBody CreateQuoteRequest request) {
        return converter.toCreateDto(
                quoteService.create(request)
        );
    }

    @GetMapping("/{quoteId}")
    public QuoteResponse get(@PathVariable Long quoteId) {
        Quote quote = quoteService.findById(quoteId).orElseThrow(
                () -> new QuoteNotFoundException(quoteId)
        );
        return converter.toQuoteDto(quote);
    }

    @PutMapping("/{quoteId}")
    public UpdateQuoteResponse update(
            @PathVariable Long quoteId,
            @RequestBody String content
    ) {
        Quote quote = quoteService.update(quoteId, content)
                .orElseThrow(() -> new QuoteNotFoundException(quoteId));
        return converter.toUpdateDto(quote);
    }

    @GetMapping("/random")
    public QuoteResponse getRandom() {
        Quote quote = quoteService.findRandom().orElseThrow(
                EmptyQuoteListException::new
        );
        return converter.toQuoteDto(quote);
    }

    @DeleteMapping("/{quoteId}")
    public QuoteResponse delete(@PathVariable Long quoteId) {
        Quote quote = quoteService.remove(quoteId)
                .orElseThrow(() -> new QuoteNotFoundException(quoteId));
        return converter.toQuoteDto(quote);
    }

}