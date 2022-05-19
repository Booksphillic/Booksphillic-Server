package com.booksphillic.domain.board;

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
public class PostImage { // 책방의 공간들

    @Id @GeneratedValue
    @Column(name = "post_image_id")
    private Long id;

    private Long postId;

    @Column(nullable = false)
    private String url;
}
