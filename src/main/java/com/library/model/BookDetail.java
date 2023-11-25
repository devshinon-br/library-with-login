package com.library.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "book_detail")
public class BookDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="page_count")
    private int pageCount;

    private String language;

    @Column(name ="available_online")
    private boolean availableOnline;

    @Column(name ="publication_date")
    private Date publicationDate;

    @OneToOne(mappedBy = "bookDetail", cascade = CascadeType.ALL)
    private Book book;

    @ManyToMany
    @JoinTable(
        name = "book_detail_tag",
        joinColumns = @JoinColumn(name = "book_detail_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();
}
