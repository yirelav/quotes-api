package com.github.yirelav.quotessolution.web.controller;

import com.github.yirelav.quotessolution.service.AuthorService;
import com.github.yirelav.quotessolution.web.dto.CreateAuthorRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@AllArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateAuthorRequest request) {
        authorService.create(request);
    }
}
