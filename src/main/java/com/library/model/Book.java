package com.library.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_detail_id", referencedColumnName = "id")
    private BookDetail bookDetail;
}
