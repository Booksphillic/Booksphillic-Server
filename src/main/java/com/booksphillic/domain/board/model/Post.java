package com.booksphillic.domain.board.model;

import com.booksphillic.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Post extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private Long storeId;

    private Long editorId;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content;

}
