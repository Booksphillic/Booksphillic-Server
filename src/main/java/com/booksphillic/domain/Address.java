package com.booksphillic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Address {

    private String city;     // 시도
    private String district; // 지역구
    private String street;   // 나머지 주소

}
