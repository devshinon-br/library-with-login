package com.library.model.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class BookResponse {
    private Long id;
    private String title;
    private AuthorResponse author;
    private PublisherResponse publisher;
    private List<GenreResponse> genres;
    private BookCoverResponse bookCover;
}