package com.library.controller;

import com.library.exception.EntityNotFoundException;
import com.library.model.BookCover;
import com.library.model.dto.request.BookCoverRequest;
import com.library.model.dto.response.BookCoverResponse;
import com.library.service.BookCoverService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/book-covers")
public class BookCoverController {
    private final BookCoverService bookCoverService;
    private final ModelMapper modelMapper;

    @Autowired
    public BookCoverController(final BookCoverService bookCoverService, final ModelMapper modelMapper) {
        this.bookCoverService = bookCoverService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/yaml"})
    public List<BookCoverResponse> getAllBookCovers() {
        final List<BookCover> bookCovers = bookCoverService.getAllBookCovers();
        return bookCovers.stream()
            .map(bookCover -> modelMapper.map(bookCover, BookCoverResponse.class))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<BookCoverResponse>> getBookCoverById(@PathVariable final Long id) {
        final BookCover bookCover = bookCoverService.getBookCoverById(id);

        if (bookCover == null) {
            throw new EntityNotFoundException("BookCover with ID " + id + " not found");
        }

        final BookCoverResponse bookCoverResponse = modelMapper.map(bookCover, BookCoverResponse.class);
        final EntityModel<BookCoverResponse> resource = EntityModel.of(bookCoverResponse);

        final Link selfLink = Link.of(WebMvcLinkBuilder.linkTo(BookCoverController.class).slash(id).withSelfRel().getHref());
        resource.add(selfLink);

        final Link bookCoversLink = Link.of(WebMvcLinkBuilder.linkTo(BookCoverController.class).withRel("bookCovers").getHref());
        resource.add(bookCoversLink);

        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<BookCoverResponse> saveBookCover(@RequestBody final BookCoverRequest bookCoverRequest) {
        final BookCover bookCover = modelMapper.map(bookCoverRequest, BookCover.class);
        final BookCover savedBookCover = bookCoverService.saveBookCover(bookCover);
        BookCoverResponse bookCoverResponse = modelMapper.map(savedBookCover, BookCoverResponse.class);
        return new ResponseEntity<>(bookCoverResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookCoverResponse> updateBookCover(@PathVariable final Long id,
                                                             @RequestBody final BookCoverRequest bookCoverRequest) {
        final BookCover existingBookCover = bookCoverService.getBookCoverById(id);

        if (existingBookCover == null) {
            throw new EntityNotFoundException("BookCover with ID " + id + " not found");
        }

        modelMapper.map(bookCoverRequest, existingBookCover);
        final BookCover savedBookCover = bookCoverService.saveBookCover(existingBookCover);
        BookCoverResponse bookCoverResponse = modelMapper.map(savedBookCover, BookCoverResponse.class);
        return ResponseEntity.ok(bookCoverResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookCover(@PathVariable final Long id) {
        bookCoverService.deleteBookCover(id);
        return ResponseEntity.noContent().build();
    }
}
