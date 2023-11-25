package com.library.service;

import com.library.model.BookCover;
import com.library.repository.BookCoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCoverService {
    private final BookCoverRepository bookCoverRepository;

    @Autowired
    public BookCoverService(final BookCoverRepository bookCoverRepository) {
        this.bookCoverRepository = bookCoverRepository;
    }

    public List<BookCover> getAllBookCovers() {
        return bookCoverRepository.findAll();
    }

    public BookCover getBookCoverById(final Long id) {
        return bookCoverRepository.findById(id).orElse(null);
    }

    public BookCover saveBookCover(final BookCover bookCover) {
        return bookCoverRepository.save(bookCover);
    }

    public void deleteBookCover(final Long id) {
        bookCoverRepository.deleteById(id);
    }
}
