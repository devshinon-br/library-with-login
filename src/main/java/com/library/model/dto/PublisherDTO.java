package com.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PublisherDTO {
    private Long id;
    private String name;
    private List<Long> bookIds;
}
