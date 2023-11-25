package com.library.service;

import com.library.model.Book;
import com.library.model.BookCover;
import com.library.model.Genre;
import com.library.repository.BookCoverRepository;
import com.library.repository.BookRepository;
import com.library.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookCoverRepository bookCoverRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public BookService(final BookRepository bookRepository,
                       final BookCoverRepository bookCoverRepository,
                       final GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.bookCoverRepository = bookCoverRepository;
        this.genreRepository = genreRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(final Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book saveBook(final Book book) {
        return bookRepository.saveAndFlush(book);
    }

    public void deleteBook(final Long id) {
        bookRepository.deleteById(id);
    }

    public BookCover findBookCoverByBookId(final Long id) {
        return bookCoverRepository.findBookCoverByBookId(id).orElse(null);
    }

    public List<Genre> getGenresByIds(final List<Long> genreIds) {
        return genreRepository.findByIds(genreIds);
    }
}
