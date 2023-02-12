package com.github.yirelav.quotessolution.web.dto;

import lombok.Value;

@Value
public class ErrorResponse {
    String message;

    public static ErrorResponse withMessage(String messageArg) {
        return new ErrorResponse(messageArg);
    }
}
