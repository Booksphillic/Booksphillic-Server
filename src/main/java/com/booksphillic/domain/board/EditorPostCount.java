package com.booksphillic.domain.board;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class EditorPostCount {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "post_count")
    @ColumnDefault("0")
    private int postCount;
}
