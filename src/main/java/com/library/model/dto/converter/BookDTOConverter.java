package com.library.model.dto.converter;

import com.library.model.Book;
import com.library.model.dto.BookDTO;

import java.util.List;
import java.util.stream.Collectors;

public class BookDTOConverter {

    public static BookDTO convertToDTO(final Book book) {
        if (book == null) {
            return null;
        }
        return new BookDTO(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getPublisher().getId());
    }

    public static List<BookDTO> convertToDTOList(final List<Book> books) {
        return books.stream()
            .map(BookDTOConverter::convertToDTO)
            .collect(Collectors.toList());
    }

    public static Book convertToEntity(final BookDTO bookDTO) {
        if (bookDTO == null) {
            return null;
        }
        final Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());

        return book;
    }

    public static Book updateEntityFromDTO(final Book existingBook, final BookDTO bookDTO) {
        if (existingBook == null || bookDTO == null) {
            return null;
        }
        existingBook.setTitle(bookDTO.getTitle());

        return existingBook;
    }
}
