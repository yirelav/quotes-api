package com.github.yirelav.quotessolution.service;

import com.github.yirelav.quotessolution.domain.entities.Author;
import com.github.yirelav.quotessolution.repository.AuthorRepository;
import com.github.yirelav.quotessolution.web.dto.CreateAuthorRequest;
import com.github.yirelav.quotessolution.web.exception.AuthorAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AuthorService {

    private final AuthorRepository repository;

    @Modifying
    public Author create(CreateAuthorRequest request) {

        if (findByName(request.getName()).isPresent()) {
            throw new AuthorAlreadyExistsException();
        }

        Author author = Author.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(
                        BCrypt.hashpw(
                                request.getPassword(),
                                BCrypt.gensalt()
                        )
                ).build();
        return repository.save(author);
    }

    public Optional<Author> findByName(String name) {
        return repository.findByNameIgnoreCase(name);
    }
}
