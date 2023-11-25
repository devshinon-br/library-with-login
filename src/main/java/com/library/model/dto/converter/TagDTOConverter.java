package com.library.model.dto.converter;

import com.library.model.BookDetail;
import com.library.model.Tag;
import com.library.model.dto.TagDTO;

import java.util.List;
import java.util.stream.Collectors;

public class TagDTOConverter {

    public static TagDTO convertToDTO(final Tag tag) {
        if (tag == null) {
            return null;
        }

        return new TagDTO(
            tag.getId(),
            tag.getName(),
            getBookDetailIds(tag.getBookDetails())
        );
    }

    public static List<TagDTO> convertToDTOList(final List<Tag> tags) {
        return tags.stream()
            .map(TagDTOConverter::convertToDTO)
            .collect(Collectors.toList());
    }

    public static Tag convertToEntity(final TagDTO tagDTO) {
        if (tagDTO == null) {
            return null;
        }
        final Tag tag = new Tag();
        tag.setId(tagDTO.getId());
        tag.setName(tagDTO.getName());
        return tag;
    }

    public static Tag updateEntityFromDTO(final Tag existingTag, final TagDTO tagDTO) {
        if (existingTag == null || tagDTO == null) {
            return null;
        }
        existingTag.setId(tagDTO.getId());
        existingTag.setName(tagDTO.getName());
        return existingTag;
    }

    private static List<Long> getBookDetailIds(final List<BookDetail> bookDetails) {
        return bookDetails.stream()
            .map(BookDetail::getId)
            .collect(Collectors.toList());
    }

    public static List<Tag> convertToEntityListFromIds(final List<Long> tagIds) {
        return tagIds.stream()
            .map(TagDTOConverter::convertToEntityFromId)
            .collect(Collectors.toList());
    }

    public static Tag convertToEntityFromId(final Long tagId) {
        final Tag tag = new Tag();
        tag.setId(tagId);
        return tag;
    }
}

