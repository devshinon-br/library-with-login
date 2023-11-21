package com.library.service;

import com.library.exception.EntityNotFoundException;
import com.library.model.BookDetail;
import com.library.repository.BookDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookDetailService {

    private final BookDetailRepository bookDetailRepository;

    @Autowired
    public BookDetailService(final BookDetailRepository bookDetailRepository) {
        this.bookDetailRepository = bookDetailRepository;
    }

    public List<BookDetail> getAllBookDetails() {
        return bookDetailRepository.findAll();
    }

    public BookDetail getBookDetailById(final Long id) {
        return bookDetailRepository.findById(id).orElse(null);
    }

    public BookDetail saveBookDetail(final BookDetail bookDetail) {
        return bookDetailRepository.save(bookDetail);
    }

    public BookDetail updateBookDetail(final Long id, final BookDetail bookDetail) {
        BookDetail existingBookDetail = bookDetailRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("BookDetail with ID " + id + " not found"));

        existingBookDetail.setGenre(bookDetail.getGenre());
        existingBookDetail.setPageCount(bookDetail.getPageCount());
        existingBookDetail.setLanguage(bookDetail.getLanguage());
        existingBookDetail.setAvailableOnline(bookDetail.isAvailableOnline());
        existingBookDetail.setPublicationDate(bookDetail.getPublicationDate());

        return bookDetailRepository.save(existingBookDetail);
    }

    public void deleteBookDetail(final Long id) {
        bookDetailRepository.deleteById(id);
    }
}

