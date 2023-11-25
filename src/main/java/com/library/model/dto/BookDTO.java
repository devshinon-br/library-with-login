package com.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BookDTO implements Serializable {
    private String title;
    private Long authorId;
    private Long publisherId;
}
