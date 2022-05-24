package com.booksphillic.service.inquiry.dto;

import com.booksphillic.domain.inquiry.InquiryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostInquiryReq {

    private Long storeId;
    private Long inquirerId;
    private InquiryType type;
    private boolean open;
    private String title;
    private String content;
}
