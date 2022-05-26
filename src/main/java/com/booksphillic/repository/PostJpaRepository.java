package com.booksphillic.repository;

import com.booksphillic.domain.board.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long postId);
    Page<Post> findByEditorId(Long editorId, Pageable pageable);

    // 특정 에디터가 작성한 글의 개수 조회
    long countByEditorId(Long editorId);


}
