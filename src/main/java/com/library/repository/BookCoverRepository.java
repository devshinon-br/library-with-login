package com.library.repository;

import com.library.model.BookCover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookCoverRepository extends JpaRepository<BookCover, Long> {
    @Query("SELECT bc FROM BookCover bc WHERE bc.book.id = :bookId")
    Optional<BookCover> findBookCoverByBookId(@Param("bookId") final Long bookId);
}
