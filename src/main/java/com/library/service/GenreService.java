package com.library.service;

import com.library.model.Genre;
import com.library.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(final GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(final Long id) {
        return genreRepository.findById(id).orElse(null);
    }

    public Genre saveGenre(final Genre genre) {
        return genreRepository.save(genre);
    }

    public void deleteGenre(final Long id) {
        genreRepository.deleteById(id);
    }
}
