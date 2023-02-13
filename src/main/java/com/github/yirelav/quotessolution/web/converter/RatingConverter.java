package com.github.yirelav.quotessolution.web.converter;

import com.github.yirelav.quotessolution.domain.entities.RatingHistoryRecord;
import com.github.yirelav.quotessolution.web.dto.RatingHistoryRecordResponse;
import org.springframework.stereotype.Component;

@Component
public class RatingConverter {

    public RatingHistoryRecordResponse toRatingResponse(RatingHistoryRecord entity) {
        return new RatingHistoryRecordResponse(
                entity.getId(),
                entity.getDifference(),
                entity.getTotal(),
                entity.getAuthor().getName(),
                entity.getDate()
        );
    }
}
