package com.library.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "book_cover")
public class BookCover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;
}
