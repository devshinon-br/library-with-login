package com.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class BookDetailDTO {
    private String genre;
    private int pageCount;
    private String language;
    private boolean availableOnline;
    private Date publicationDate;
}
