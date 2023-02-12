package com.github.yirelav.quotessolution.web.dto;

import lombok.Value;

@Value
public class CreateAuthorRequest {
    String name;
    String email;
    String password;
}
