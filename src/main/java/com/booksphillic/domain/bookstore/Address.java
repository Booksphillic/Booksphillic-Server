package com.booksphillic.domain.bookstore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Address {

    private String city;     // 시도

    @Enumerated(EnumType.STRING)
    private DistrictType district; // 지역구

    private String street;   // 나머지 주소

    @Override
    public String toString() {
        return this.getCity() + " " + this.getDistrict().getKo() + " " +this.getStreet();
    }
}
