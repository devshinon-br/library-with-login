package com.library.model.dto.converter;

import com.library.model.Author;
import com.library.model.Book;
import com.library.model.dto.AuthorDTO;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorDTOConverter {

    public static AuthorDTO convertToDTO(final Author author) {
        if (author == null) {
            return null;
        }
        return new AuthorDTO(
            author.getName(),
            getBookIds(author.getBooks()));
    }

    public static List<AuthorDTO> convertToDTOList(final List<Author> authors) {
        return authors.stream()
            .map(AuthorDTOConverter::convertToDTO)
            .collect(Collectors.toList());
    }

    public static Author convertToEntity(final AuthorDTO authorDTO) {
        if (authorDTO == null) {
            return null;
        }
        final Author author = new Author();
        author.setName(authorDTO.getName());
        return author;
    }

    public static Author updateEntityFromDTO(final Author existingAuthor, final AuthorDTO authorDTO) {
        if (existingAuthor == null || authorDTO == null) {
            return null;
        }
        existingAuthor.setName(authorDTO.getName());
        return existingAuthor;
    }

    private static List<Long> getBookIds(final List<Book> books) {
        return books.stream()
            .map(Book::getId)
            .collect(Collectors.toList());
    }
}

