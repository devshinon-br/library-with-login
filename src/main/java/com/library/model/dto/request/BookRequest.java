package com.library.model.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookRequest {
    private String title;
    private Long authorId;
    private Long publisherId;
    private List<Long> genreIds = new ArrayList<Long>();
}
