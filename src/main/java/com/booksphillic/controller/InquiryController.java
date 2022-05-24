package com.booksphillic.controller;

import com.booksphillic.domain.inquiry.InquiryType;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponse;
import com.booksphillic.service.inquiry.InquiryService;
import com.booksphillic.service.inquiry.dto.GetInquiryRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/inquiry")
@RestController
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @GetMapping("/list")
    public BaseResponse<List<GetInquiryRes>> getInquiries(@RequestParam Long userId,
                                                          @RequestParam(name = "public", defaultValue = "false") boolean onlyPublic,
                                                          @RequestParam(required = false) InquiryType type,
                                                          @RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "4") int size){
        try {
            List<GetInquiryRes> getInquiries;
            if(type == null) getInquiries = inquiryService.getInquiries(userId, onlyPublic, page, size);
            else getInquiries = inquiryService.getInquiriesByType(userId, onlyPublic, type, page, size);

            return new BaseResponse<>(getInquiries);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }
}
