package com.booksphillic.domain.bookstore;

import lombok.Getter;

@Getter
public enum DistrictType {

    GANGNAM("강남구", "GANGNAM"),
    GANGDONG("강북구", "GANGDONG"),
    GANGSEO("강서구", "GANGSEO"),
    GWANAK("관악구", "GWANAK"),
    GWANGJIN("광진구", "GWANGJIN"),
    GURO("구로구", "GURO"),
    GEUMCHEON("금천구", "GEUMCHEON"),
    NOWON("노원구", "NOWON"),
    DOBONG("도봉구", "DOBONG"),
    DONGDAEMUN("동대문구", "DONGDAEMUN"),
    DONGJAK("동작구", "DONGJAK"),
    MAPO("마포구", "MAPO"),
    SEODEMUN("서대문구", "SEODEMUN"),
    SEOCHO("서초구", "SEOCHO"),
    SEONGDONG("성동구", "SEONGDONG"),
    SEONGBUK("성북구", "SEONGBUK"),
    SONGPA("송파구", "SONGPA"),
    YANGCHEON("양천구", "YANGCHEON"),
    YEONGDEUNGPO("영등포구", "YEONGDEUNGPO"),
    YONGSAN("용산구", "YONGSAN"),
    EUNPYEONG("은평구", "EUNPYEONG"),
    JONGNO("종로구", "JONGNO"),
    JUNGGU("중구", "JUNGGU"),
    JUNGNANG("중랑구", "JUNGNANG");


    private String ko;
    private String en;

    DistrictType(String ko, String en) {
        this.ko = ko;
        this.en = en;
    }

    public String getKo() {
        return ko;
    }

    public String getEn() {
        return en;
    }
}
