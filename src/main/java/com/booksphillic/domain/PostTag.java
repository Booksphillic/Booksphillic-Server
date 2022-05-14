package com.booksphillic.domain;

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
public class PostTag {

    @Id @GeneratedValue
    @Column(name = "post_tag_id")
    private Long id;

    private Long postId;

    private Long tagId;
}
