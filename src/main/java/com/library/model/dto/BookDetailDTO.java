package com.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class BookDetailDTO implements Serializable {
    private int pageCount;
    private String language;
    private boolean availableOnline;
    private Date publicationDate;
}
