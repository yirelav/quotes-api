package com.github.yirelav.quotessolution.web.converter;

import com.github.yirelav.quotessolution.domain.entities.Quote;
import com.github.yirelav.quotessolution.web.dto.CreateQuoteResponse;
import com.github.yirelav.quotessolution.web.dto.QuoteResponse;
import com.github.yirelav.quotessolution.web.dto.UpdateQuoteResponse;
import org.springframework.stereotype.Component;

@Component
public class QuoteConverter {
    public CreateQuoteResponse toCreateResponse(Quote quote) {
        return new CreateQuoteResponse(quote.getId(), quote.getAuthor().getName(), quote.getContent());
    }

    public UpdateQuoteResponse toUpdateResponse(Quote quote) {
        return new UpdateQuoteResponse(quote.getId(), quote.getAuthor().getName(), quote.getContent());

    }

    public QuoteResponse toQuoteResponse(Quote quote) {
        return new QuoteResponse(
                quote.getId(),
                quote.getAuthor().getName(),
                quote.getContent(),
                quote.getUpdated(),
                quote.getCurrentRating()
        );
    }
}
