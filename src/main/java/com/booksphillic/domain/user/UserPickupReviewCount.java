package com.booksphillic.domain.user;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserPickupReviewCount {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "pickup_count")
    @ColumnDefault("0")
    private int pickupCount;

    @Column(name = "review_count")
    @ColumnDefault("0")
    private int reviewCount;

    @Column(name = "phillic")
    @ColumnDefault("0")
    private int phillic;
}
