package com.booksphillic.repository;

import com.booksphillic.domain.board.Editor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EditorRepository extends JpaRepository<Editor, Long> {

    Optional<Editor> findById(Long editorId);
}
