package com.github.yirelav.quotessolution.web.dto;

import java.time.Instant;

public record RatingHistoryRecordResponse(Long id, Integer difference, Integer total, String author, Instant date) {
}
