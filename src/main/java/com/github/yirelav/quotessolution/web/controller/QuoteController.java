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
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

import java.util.List;

@RestController
@RequestMapping("/quotes")
@AllArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;
    private final QuoteConverter converter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create quote")
    public CreateQuoteResponse add(@RequestBody CreateQuoteRequest request) {
        return converter.toCreateResponse(
                quoteService.create(request)
        );
    }

    @GetMapping("/{quoteId}")
    @Operation(description = "Read quote")
    public QuoteResponse get(@PathVariable Long quoteId) {
        Quote quote = quoteService.findById(quoteId).orElseThrow(
                () -> new QuoteNotFoundException(quoteId)
        );
        return converter.toQuoteResponse(quote);
    }

    @PutMapping("/{quoteId}")
    @Operation(description = "Update quote")
    public UpdateQuoteResponse update(
            @PathVariable Long quoteId,
            @RequestBody String content
    ) {
        Quote quote = quoteService.update(quoteId, content)
                .orElseThrow(() -> new QuoteNotFoundException(quoteId));
        return converter.toUpdateResponse(quote);
    }

    @DeleteMapping("/{quoteId}")
    @Operation(description = "Delete quote")
    public QuoteResponse delete(@PathVariable Long quoteId) {
        Quote quote = quoteService.remove(quoteId)
                .orElseThrow(() -> new QuoteNotFoundException(quoteId));
        return converter.toQuoteResponse(quote);
    }

    @GetMapping("/random")
    @Operation(description = "Get random quote")
    public QuoteResponse getRandom() {
        Quote quote = quoteService.findRandom().orElseThrow(
                EmptyQuoteListException::new
        );
        return converter.toQuoteResponse(quote);
    }

    @GetMapping("/top10")
    @Operation(description = "Get top 10 quotes")
    public List<QuoteResponse> top10() {
        PageRequest currentRating = PageRequest.of(0, 10, Sort.Direction.DESC, "currentRating");
        return quoteService.findPageAndSort(currentRating)
                .getContent().stream()
                .map(converter::toQuoteResponse)
                .toList();
    }
    @GetMapping("/worse10")
    @Operation(description = "Get worse 10 quotes")
    public List<QuoteResponse> worse10() {
        PageRequest currentRating = PageRequest.of(0, 10, Sort.Direction.ASC, "currentRating");
        return quoteService.findPageAndSort(currentRating)
                .getContent().stream()
                .map(converter::toQuoteResponse)
                .toList();
    }

    @GetMapping
    @Operation(description = "Read all quotes")
    public List<QuoteResponse> all() {
        return quoteService.findAll().stream()
                .map(converter::toQuoteResponse)
                .toList();
    }

}
