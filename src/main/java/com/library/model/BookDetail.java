package com.library.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "book_detail")
public class BookDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String genre;

    @Column(name ="page_count")
    private int pageCount;

    private String language;

    @Column(name ="available_online")
    private boolean availableOnline;

    @Column(name ="publication_date")
    private Date publicationDate;

    @OneToOne(mappedBy = "bookDetail", cascade = CascadeType.ALL)
    private Book book;
}
