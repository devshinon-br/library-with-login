package com.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class AuthorDTO implements Serializable {
    private String name;
    private List<Long> bookIds;
}
