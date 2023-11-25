package com.library.controller;

import com.library.exception.EntityNotFoundException;
import com.library.model.Author;
import com.library.model.dto.request.AuthorRequest;
import com.library.model.dto.response.AuthorResponse;
import com.library.service.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorController(final AuthorService authorService, final ModelMapper modelMapper) {
        this.authorService = authorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/yaml"})
    public List<AuthorResponse> getAllAuthors() {
        final List<Author> authors = authorService.getAllAuthors();
        return authors.stream()
            .map(author -> modelMapper.map(author, AuthorResponse.class))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable final Long id) {
        final Author author = authorService.getAuthorById(id);

        if (author == null) {
            throw new EntityNotFoundException("Author with ID " + id + " not found");
        }

        final AuthorResponse authorResponse = modelMapper.map(author, AuthorResponse.class);
        return ResponseEntity.ok(authorResponse);
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> saveAuthor(@RequestBody final AuthorRequest authorRequest) {
        final Author author = modelMapper.map(authorRequest, Author.class);
        final Author savedAuthor = authorService.saveAuthor(author);
        final AuthorResponse authorResponse = modelMapper.map(savedAuthor, AuthorResponse.class);
        return new ResponseEntity<>(authorResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(@PathVariable final Long id, @RequestBody final AuthorRequest authorRequest) {
        final Author existingAuthor = authorService.getAuthorById(id);

        if (existingAuthor == null) {
            throw new EntityNotFoundException("Author with ID " + id + " not found");
        }

        modelMapper.map(authorRequest, existingAuthor);
        final Author savedAuthor = authorService.saveAuthor(existingAuthor);
        final AuthorResponse authorResponse = modelMapper.map(savedAuthor, AuthorResponse.class);
        return ResponseEntity.ok(authorResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable final Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}

