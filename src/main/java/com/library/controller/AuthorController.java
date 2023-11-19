package com.library.controller;

import com.library.exception.EntityNotFoundException;
import com.library.model.Author;
import com.library.model.dto.AuthorDTO;
import com.library.model.dto.converter.AuthorDTOConverter;
import com.library.service.AuthorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@Tag(name = "Author", description = "Endpoint for managing authors.")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(final AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorDTO> getAllAuthors() {
        final List<Author> authors = authorService.getAllAuthors();
        return AuthorDTOConverter.convertToDTOList(authors);
    }

    @GetMapping("/{id}")
    public AuthorDTO getAuthorById(@PathVariable final Long id) {
        final Author author = authorService.getAuthorById(id);
        return AuthorDTOConverter.convertToDTO(author);
    }

    @PostMapping
    public AuthorDTO saveAuthor(@RequestBody final AuthorDTO authorDTO) {
        final Author author = AuthorDTOConverter.convertToEntity(authorDTO);
        final Author savedAuthor = authorService.saveAuthor(author);
        return AuthorDTOConverter.convertToDTO(savedAuthor);
    }

    @PutMapping("/{id}")
    public AuthorDTO updateAuthor(@PathVariable final Long id, @RequestBody final AuthorDTO authorDTO) {
        final Author existingAuthor = authorService.getAuthorById(id);

        if (existingAuthor == null) {
            throw new EntityNotFoundException("Author with ID " + id + " not found");
        }

        final Author updatedAuthor = AuthorDTOConverter.updateEntityFromDTO(existingAuthor, authorDTO);
        final Author savedAuthor = authorService.saveAuthor(updatedAuthor);
        return AuthorDTOConverter.convertToDTO(savedAuthor);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable final Long id) {
        authorService.deleteAuthor(id);
    }
}

