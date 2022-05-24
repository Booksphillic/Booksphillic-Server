package com.booksphillic.service.user.dto;

import com.booksphillic.domain.user.Inquiry;
import com.booksphillic.domain.user.InquiryStatus;
import com.booksphillic.domain.user.InquiryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetOwnerInquiryRes {

    private Long inquiryId;
    private Long inquirerId;
    private Long ownerId;
    private LocalDateTime createdAt;
    private String content;
    private InquiryType type;
    private InquiryStatus status;


    public GetOwnerInquiryRes(Inquiry inquiry) {
        this.inquiryId = inquiry.getInquiryId();
        this.inquirerId = inquiry.getInquirerId();
        this.ownerId = inquiry.getOwnerId();
        this.createdAt = inquiry.getCreatedAt();
        this.content = inquiry.getContent();
        this.type = inquiry.getType();
        this.status = inquiry.getStatus();
    }
}
