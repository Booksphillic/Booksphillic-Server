package com.booksphillic.domain.bookstore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OperatingHours {

    // 요일별 오픈 시간 - 클로즈 시간

    @Column(name = "monday_hours")
    private String mon;

    @Column(name = "tuesday_hours")
    private String tue;

    @Column(name = "wednesday_hours")
    private String wed;

    @Column(name = "thursday_hours")
    private String thu;

    @Column(name = "friday_hours")
    private String fri;

    @Column(name = "saturday_hours")
    private String sat;

    @Column(name = "sunday_hours")
    private String sun;
}
