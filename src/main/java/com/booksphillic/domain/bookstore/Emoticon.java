package com.booksphillic.domain.bookstore;

import lombok.Getter;

@Getter
public enum Emoticon {
    // 맘에 들어요, 좋아요, 추천해요, 짱이에요

    BEST("맘에 들어요"), // 맘에 들어요
    LIKE("좋아요"), // 좋아요
    RECOMMEND("추천해요"), // 추천해요
    SUPER("짱이에요"); // 짱이에요

    private String description;

    Emoticon(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
