package com.library.repository;

import com.library.model.BookDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {
}
