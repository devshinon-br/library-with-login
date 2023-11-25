package com.library.model.dto.request;

import lombok.Data;

@Data
public class BookCoverRequest {
    private String coverImageUrl;
    private Long bookId;
}
