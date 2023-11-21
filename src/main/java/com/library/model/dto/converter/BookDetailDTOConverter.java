package com.library.model.dto.converter;

import com.library.model.Book;
import com.library.model.BookDetail;
import com.library.model.dto.BookDTO;
import com.library.model.dto.BookDetailDTO;

import java.util.List;
import java.util.stream.Collectors;

public class BookDetailDTOConverter {

    public static BookDetailDTO convertToDTO(final BookDetail bookDetail) {
        if (bookDetail == null) {
            return null;
        }

        return new BookDetailDTO(
            bookDetail.getGenre(),
            bookDetail.getPageCount(),
            bookDetail.getLanguage(),
            bookDetail.isAvailableOnline(),
            bookDetail.getPublicationDate()
        );
    }

    public static List<BookDetailDTO> convertToDTOList(final List<BookDetail> bookDetails) {
        return bookDetails.stream()
            .map(BookDetailDTOConverter::convertToDTO)
            .collect(Collectors.toList());
    }

    public static BookDetail convertToEntity(final BookDetailDTO bookDetailDTO) {
        if (bookDetailDTO == null) {
            return null;
        }

        BookDetail bookDetail = new BookDetail();
        bookDetail.setGenre(bookDetailDTO.getGenre());
        bookDetail.setPageCount(bookDetailDTO.getPageCount());
        bookDetail.setLanguage(bookDetailDTO.getLanguage());
        bookDetail.setAvailableOnline(bookDetailDTO.isAvailableOnline());
        bookDetail.setPublicationDate(bookDetailDTO.getPublicationDate());

        return bookDetail;
    }

    public static BookDetail updateEntityFromDTO(final BookDetail existingBookDetail, final BookDetailDTO bookDetailDTO) {
        if (existingBookDetail == null || bookDetailDTO == null) {
            return null;
        }

        existingBookDetail.setGenre(bookDetailDTO.getGenre());
        existingBookDetail.setPageCount(bookDetailDTO.getPageCount());
        existingBookDetail.setLanguage(bookDetailDTO.getLanguage());
        existingBookDetail.setAvailableOnline(bookDetailDTO.isAvailableOnline());
        existingBookDetail.setPublicationDate(bookDetailDTO.getPublicationDate());

        return existingBookDetail;
    }
}