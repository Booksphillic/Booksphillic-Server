package com.booksphillic.repository.user;

import com.booksphillic.domain.user.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    List<Scrap> findByUserId(Long userId);

    Optional<Scrap> findByUserIdAndStoreId(Long userId, Long storeId);
}
