package com.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthorDTO {
    private Long id;
    private String name;
    private List<Long> bookIds;
}
