package com.booksphillic.domain;

public enum BookGenre {

    ESSAY("에세이", "ESSAY"),
    PHOTO_BOOK("포토북", "PHOTO_BOOK"),
    FAIRYTALE("동화", "FAIRYTALE"),
    PURE_LITERATURE("순수문학", "PURE_LITERATURE"),
    GENRE_LITERATURE("장르문학", "GENRE_LITERATURE"),
    POETRY_COLLECTION("시집", "POETRY_COLLECTION"),
    PODIUM("단상집", "PODIUM"),
    NOVEL("소설", "NOVEL"),
    FANTASY("판타지", "FANTASY"),
    SF("공상과학", "SF"),
    FOREIGN_LANGUAGE("외국어", "FOREIGN_LANGUAGE"),
    AUTOBIOGRAPHY("자서전", "AUTOBIOGRAPHY"),
    SELF_IMPROVEMENT("자기계발", "SELF_IMPROVEMENT"),
    DETECTIVE_NOVEL("추리소설", "DETECTIVE_NOVEL"),
    THRILLER("스릴러", "THRILLER"),
    CLASSIC_NOVEL("고전소설", "CLASSIC_NOVEL"),
    HUMANITY("인문", "HUMANITY"),
    SOCIETY("사회", "SOCIETY"),
    POLITICS("정치", "POLITICS"),
    SCIENCE("과학", "SCIENCE"),
    HISTORY("역사", "HISTORY"),
    ECONOMY("경제", "ECONOMY"),
    ART("예술", "ART"),
    PHILOSOPHY("철학", "PHILOSOPHY"),
    QUOTES("명언집", "QUOTES"),
    ROMANCE("로맨스", "ROMANCE"),
    DICTIONARY("사전", "DICTIONARY"),
    DOROK("도록", "DOROK"),
    HOBBY("취미", "HOBBY"),
    COOKING("요리", "COOKING"),
    CHILDREN("아동", "CHILDREN");


    private String ko;
    private String en;

    BookGenre(String ko, String en) {
        this.ko = ko;
        this.en = en;
    }
}
