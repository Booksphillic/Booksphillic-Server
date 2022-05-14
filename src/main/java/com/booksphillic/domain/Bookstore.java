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
public class Bookstore {

    @Id @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "profile_img", length= 100, nullable = false)
    private String profileImgUrl;

    // 내부 이미지?

    @Embedded
    private OperatingHours hours;

    @Column(nullable = false)
    private String description;

    @Embedded
    private Address address;

    @Column(name = "website")
    private String website;

    @Column(name = "email")
    private String email;

    @Column(name = "contact", length = 11)
    private String contact; // 전화번호

    @Column(name = "number_seats")
    private int numberSeats;

    @Column(name = "facility")
    private String facility; // ex) 와이파이


}
