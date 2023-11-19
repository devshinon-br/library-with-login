package com.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private Long authorId;
    private Long publisherId;
}
