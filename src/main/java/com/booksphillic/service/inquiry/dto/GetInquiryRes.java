package com.booksphillic.service.inquiry.dto;

import com.booksphillic.domain.inquiry.Inquiry;
import com.booksphillic.domain.inquiry.InquiryStatus;
import com.booksphillic.domain.inquiry.InquiryType;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class GetInquiryRes {

    private Long inquiryId;
    private Long storeId;
    private Long inquirerId;
    private LocalDateTime createdAt;
    private String title;
    private String content;
    private InquiryType type;
    private InquiryStatus status;


    public GetInquiryRes(Inquiry inquiry) {
        this.inquiryId = inquiry.getInquiryId();
        this.inquirerId = inquiry.getInquirerId();
        this.storeId = inquiry.getStoreId();
        this.createdAt = inquiry.getCreatedAt();
        this.title = inquiry.getTitle();
        this.content = inquiry.getContent();
        this.type = inquiry.getType();
        this.status = inquiry.getStatus();
    }

}
