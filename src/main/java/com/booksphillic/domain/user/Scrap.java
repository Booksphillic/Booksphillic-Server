package com.booksphillic.domain.user;

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
public class Scrap {

    @Id @GeneratedValue
    @Column(name = "scrap_id")
    private Long id;

    private Long userId;

    private Long storeId;

}
