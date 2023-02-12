package com.github.yirelav.quotessolution.web.dto;

import java.time.Instant;

public record QuoteResponse(Long id, String author, String content, Instant date, Integer rating) {
}