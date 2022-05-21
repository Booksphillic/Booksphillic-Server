package com.booksphillic.repository.user;

import com.booksphillic.domain.user.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    List<Scrap> findByUserId(Long userId);
}
