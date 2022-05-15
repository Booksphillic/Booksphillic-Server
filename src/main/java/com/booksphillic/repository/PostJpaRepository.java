package com.booksphillic.repository;

import com.booksphillic.domain.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

    public Optional<Post> findById(Long postId);

}
