package com.booksphillic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Pickup extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "pickup_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Bookstore bookstore;

    @Column(name="pickup_date", nullable = false)
    private LocalDateTime pickupDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private BookGenre bookGenre;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private PickupStatus pickupStatus;

    @Lob
    @Column(name = "requirements")
    private String requirements;
}
