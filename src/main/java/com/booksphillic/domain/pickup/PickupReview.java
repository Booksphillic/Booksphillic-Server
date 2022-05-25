package com.booksphillic.domain.pickup;

import com.booksphillic.domain.BaseEntity;
import com.booksphillic.domain.bookstore.Emoticon;
import com.booksphillic.domain.user.User;
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
public class PickupReview extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "pickup_review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pickup_id")
    private Pickup pickup;

    @Lob
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private Emoticon emoticon;
}
