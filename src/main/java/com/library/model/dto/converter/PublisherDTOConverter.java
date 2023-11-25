package com.library.model.dto.converter;

import com.library.model.Book;
import com.library.model.Publisher;
import com.library.model.dto.PublisherDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PublisherDTOConverter {

    public static PublisherDTO convertToDTO(final Publisher publisher) {
        if (publisher == null) {
            return null;
        }
        return new PublisherDTO(
            publisher.getId(),
            publisher.getName(),
            getBookIds(publisher.getBooks())
        );
    }

    public static List<PublisherDTO> convertToDTOList(final List<Publisher> publishers) {
        return publishers.stream()
            .map(PublisherDTOConverter::convertToDTO)
            .collect(Collectors.toList());
    }

    public static Publisher convertToEntity(final PublisherDTO publisherDTO) {
        if (publisherDTO == null) {
            return null;
        }
        final Publisher publisher = new Publisher();
        publisher.setId(publisherDTO.getId());
        publisher.setName(publisherDTO.getName());
        return publisher;
    }

    public static Publisher updateEntityFromDTO(final Publisher existingPublisher, final PublisherDTO publisherDTO) {
        if (existingPublisher == null || publisherDTO == null) {
            return null;
        }
        existingPublisher.setId(publisherDTO.getId());
        existingPublisher.setName(publisherDTO.getName());
        return existingPublisher;
    }

    private static List<Long> getBookIds(final List<Book> books) {
        return books.stream()
            .map(Book::getId)
            .collect(Collectors.toList());
    }
}
