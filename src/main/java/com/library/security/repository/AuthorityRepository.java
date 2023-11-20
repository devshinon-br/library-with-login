package com.library.security.repository;

import com.library.security.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    @Query("SELECT a FROM Authority a WHERE a.user.username = :username")
    List<Authority> findAuthoritiesByUserName(final String username);
}
