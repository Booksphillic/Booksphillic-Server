package com.booksphillic.domain.board;

import com.booksphillic.domain.BaseEntity;
import com.booksphillic.domain.bookstore.Bookstore;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Bookstore bookstore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editor_id")
    private Editor editor;

    @Column(nullable = false, length = 100)
    private String title;

    @Enumerated(EnumType.STRING)
    private PostCategory category;

    @Lob
    private String content1;

    @Lob
    private String content2;

    @Column(name = "content1_img_url")
    private String content1ImgUrl;

    @Column(name = "content2_img_url")
    private String content2ImgUrl;

}
