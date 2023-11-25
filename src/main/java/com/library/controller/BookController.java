package com.library.controller;

import com.library.exception.EntityNotFoundException;
import com.library.model.Book;
import com.library.model.BookCover;
import com.library.model.Genre;
import com.library.model.dto.request.BookRequest;
import com.library.model.dto.response.BookCoverResponse;
import com.library.model.dto.response.BookResponse;
import com.library.service.BookService;
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
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final ModelMapper modelMapper;

    @Autowired
    public BookController(final BookService bookService, final ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/yaml"})
    public List<BookResponse> getAllBooks() {
        final List<Book> books = bookService.getAllBooks();

        return books.stream()
            .map(book -> {
                final BookResponse bookResponse = modelMapper.map(book, BookResponse.class);
                final BookCover bookCover = bookService.findBookCoverByBookId(book.getId());
                if (bookCover != null) {
                    bookResponse.setBookCover(modelMapper.map(bookCover, BookCoverResponse.class));
                }
                return bookResponse;
            })
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<BookResponse>> getBookById(@PathVariable final Long id) {
        final Book book = bookService.getBookById(id);

        if (book == null) {
            throw new EntityNotFoundException("Book with ID " + id + " not found");
        }

        final BookResponse bookResponse = modelMapper.map(book, BookResponse.class);
        final EntityModel<BookResponse> resource = EntityModel.of(bookResponse);

        final Link selfLink = Link.of(WebMvcLinkBuilder.linkTo(BookController.class).slash(id).withSelfRel().getHref());
        resource.add(selfLink);

        final Link booksLink = Link.of(WebMvcLinkBuilder.linkTo(BookController.class).withRel("books").getHref());
        resource.add(booksLink);

        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<BookResponse> saveBook(@RequestBody final BookRequest bookRequest) {
        final Book book = modelMapper.map(bookRequest, Book.class);

        final List<Genre> genres = bookService.getGenresByIds(bookRequest.getGenreIds());

        book.setGenres(genres);

        final Book savedBook = bookService.saveBook(book);
        final BookResponse bookResponse = modelMapper.map(savedBook, BookResponse.class);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable final Long id, @RequestBody final BookRequest bookRequest) {
        final Book existingBook = bookService.getBookById(id);

        if (existingBook == null) {
            throw new EntityNotFoundException("Book with ID " + id + " not found");
        }

        modelMapper.map(bookRequest, existingBook);
        final Book savedBook = bookService.saveBook(existingBook);
        BookResponse bookResponse = modelMapper.map(savedBook, BookResponse.class);
        return ResponseEntity.ok(bookResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable final Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
