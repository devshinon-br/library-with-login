package com.library.controller;

import com.library.exception.EntityNotFoundException;
import com.library.model.Genre;
import com.library.model.dto.request.GenreRequest;
import com.library.model.dto.response.GenreResponse;
import com.library.service.GenreService;
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
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;
    private final ModelMapper modelMapper;

    @Autowired
    public GenreController(final GenreService genreService, final ModelMapper modelMapper) {
        this.genreService = genreService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/yaml"})
    public List<GenreResponse> getAllGenres() {
        final List<Genre> genres = genreService.getAllGenres();
        return genres.stream()
            .map(genre -> modelMapper.map(genre, GenreResponse.class))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponse> getGenreById(@PathVariable final Long id) {
        final Genre genre = genreService.getGenreById(id);

        if (genre == null) {
            throw new EntityNotFoundException("Genre with ID " + id + " not found");
        }

        GenreResponse genreResponse = modelMapper.map(genre, GenreResponse.class);
        return ResponseEntity.ok(genreResponse);
    }

    @PostMapping
    public ResponseEntity<GenreResponse> saveGenre(@RequestBody final GenreRequest genreRequest) {
        final Genre genre = modelMapper.map(genreRequest, Genre.class);
        final Genre savedGenre = genreService.saveGenre(genre);
        GenreResponse genreResponse = modelMapper.map(savedGenre, GenreResponse.class);
        return new ResponseEntity<>(genreResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreResponse> updateGenre(@PathVariable final Long id, @RequestBody final GenreRequest genreRequest) {
        final Genre existingGenre = genreService.getGenreById(id);

        if (existingGenre == null) {
            throw new EntityNotFoundException("Genre with ID " + id + " not found");
        }

        modelMapper.map(genreRequest, existingGenre);
        final Genre savedGenre = genreService.saveGenre(existingGenre);
        GenreResponse genreResponse = modelMapper.map(savedGenre, GenreResponse.class);
        return ResponseEntity.ok(genreResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable final Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}

