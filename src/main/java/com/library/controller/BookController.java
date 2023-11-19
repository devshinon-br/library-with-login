package com.library.controller;

import com.library.exception.EntityNotFoundException;
import com.library.model.Book;
import com.library.model.dto.BookDTO;
import com.library.model.dto.converter.BookDTOConverter;
import com.library.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Tag(name = "Book", description = "Endpoint for managing books.")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        final List<Book> books = bookService.getAllBooks();
        return BookDTOConverter.convertToDTOList(books);
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable final Long id) {
        final Book book = bookService.getBookById(id);
        return BookDTOConverter.convertToDTO(book);
    }

    @PostMapping
    public BookDTO saveBook(@RequestBody final BookDTO bookDTO) {
        final Book book = BookDTOConverter.convertToEntity(bookDTO);
        final Book savedBook = bookService.saveBook(book);
        return BookDTOConverter.convertToDTO(savedBook);
    }

    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable final Long id, @RequestBody final BookDTO bookDTO) {
        final Book existingBook = bookService.getBookById(id);

        if (existingBook == null) {
            throw new EntityNotFoundException("Book with ID " + id + " not found");
        }

        final Book updatedBook = BookDTOConverter.updateEntityFromDTO(existingBook, bookDTO);
        final Book savedBook = bookService.saveBook(updatedBook);
        return BookDTOConverter.convertToDTO(savedBook);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable final Long id) {
        bookService.deleteBook(id);
    }
}
