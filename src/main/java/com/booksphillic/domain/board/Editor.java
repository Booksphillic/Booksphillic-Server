package com.booksphillic.domain.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Editor {

    @Id @GeneratedValue
    @Column(name = "editor_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(length = 100)
    private String description;

    @Column(name = "profile_img")
    private String profileImgUrl;

}
