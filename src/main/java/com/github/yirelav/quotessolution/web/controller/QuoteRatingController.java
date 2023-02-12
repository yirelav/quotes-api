package com.github.yirelav.quotessolution.web.controller;

import com.github.yirelav.quotessolution.domain.entities.RatingHistoryRecord;
import com.github.yirelav.quotessolution.domain.enums.Vote;
import com.github.yirelav.quotessolution.service.QuoteService;
import com.github.yirelav.quotessolution.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quotes")
@AllArgsConstructor
public class QuoteRatingController {

    private final RatingService ratingService;
    private final QuoteService quoteService;

    @PostMapping("/{quoteId}/up")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void voteUp(@PathVariable long quoteId) {
        quoteService.changeRating(quoteId, Vote.UP);
    }
    @PostMapping("/{quoteId}/down")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void voteDown(@PathVariable long quoteId) {
        quoteService.changeRating(quoteId, Vote.DOWN);
    }

    @GetMapping("/{quoteId}/ratings")
    public List<RatingHistoryRecord> getRatings(@PathVariable Long quoteId) {
        return ratingService.getQuoteRatings(quoteId).toList();
    }

}
