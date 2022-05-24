package com.booksphillic.domain.inquiry;

import com.booksphillic.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Inquiry extends BaseEntity {

    @Id @GeneratedValue
    private Long inquiryId;

    private Long ownerId;           // 사장님

    private Long inquirerId;        // 문의자

    private String title;           // 제목

    @Lob
    private String content;         // 문의 내용

    @Enumerated(EnumType.STRING)
    private InquiryType type;       // 서비스, 책방, 기타

    @Enumerated(EnumType.STRING)
    private InquiryStatus status;   // 대기, 완료

    private boolean isPublic;       // 공개 여부
}
