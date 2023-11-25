package com.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class BookDetailDTO implements Serializable {
    private Long id;
    private int pageCount;
    private String language;
    private boolean availableOnline;
    private Date publicationDate;
}
