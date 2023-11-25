package com.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class PublisherDTO implements Serializable {
    private Long id;
    private String name;
    private List<Long> bookIds;
}
