package com.library.service;

import com.library.model.Author;
import com.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(final Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    public Author saveAuthor(final Author author) {
        return authorRepository.save(author);
    }

    public void deleteAuthor(final Long id) {
        authorRepository.deleteById(id);
    }
}
