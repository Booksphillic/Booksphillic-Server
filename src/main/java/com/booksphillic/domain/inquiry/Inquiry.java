package com.booksphillic.domain.inquiry;

import com.booksphillic.domain.BaseEntity;
import com.booksphillic.service.inquiry.dto.PostInquiryReq;
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

    private Long storeId;           // 서점

    private Long inquirerId;        // 문의자

    private String title;           // 제목

    @Lob
    private String content;         // 문의 내용

    @Enumerated(EnumType.STRING)
    private InquiryType type;       // 서비스, 책방, 기타

    @Enumerated(EnumType.STRING)
    private InquiryStatus status;   // 대기, 완료

    private boolean isPublic;       // 공개 여부


    public Inquiry(PostInquiryReq postInquiryReq) {
        this.storeId = postInquiryReq.getStoreId();
        this.inquirerId = postInquiryReq.getInquirerId();
        this.title = postInquiryReq.getTitle();
        this.content = postInquiryReq.getContent();
        this.type = postInquiryReq.getType();
        this.status = InquiryStatus.WAITING;
        this.isPublic = postInquiryReq.isOpen();
    }
}
