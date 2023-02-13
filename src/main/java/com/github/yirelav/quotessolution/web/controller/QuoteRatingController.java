package com.github.yirelav.quotessolution.web.controller;

import com.github.yirelav.quotessolution.domain.enums.Vote;
import com.github.yirelav.quotessolution.service.QuoteService;
import com.github.yirelav.quotessolution.service.RatingService;
import com.github.yirelav.quotessolution.web.converter.RatingConverter;
import com.github.yirelav.quotessolution.web.dto.RatingHistoryRecordResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quotes")
@AllArgsConstructor
public class QuoteRatingController {

    private final RatingConverter converter;

    private final RatingService ratingService;
    private final QuoteService quoteService;

    @PostMapping("/{quoteId}/up")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(description = "Increase quote rating")
    public void voteUp(
            @PathVariable long quoteId,
            @RequestParam String author
    ) {
        quoteService.changeRating(quoteId, author, Vote.UP);
    }

    @PostMapping("/{quoteId}/down")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(description = "Decrease quote rating")
    public void voteDown(
            @PathVariable long quoteId,
            @RequestParam String author
    ) {
        quoteService.changeRating(quoteId, author, Vote.DOWN);
    }

    @GetMapping("/{quoteId}/ratings")
    @Operation(description = "Read all quote ratings")
    public List<RatingHistoryRecordResponse> getRatings(@PathVariable Long quoteId) {
        return ratingService.getQuoteRatings(quoteId)
                .stream().map(converter::toRatingResponse)
                .toList();
    }

}
