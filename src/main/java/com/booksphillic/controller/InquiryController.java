package com.booksphillic.controller;

import com.booksphillic.domain.inquiry.InquiryType;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponse;
import com.booksphillic.service.auth.AuthService;
import com.booksphillic.service.auth.JwtService;
import com.booksphillic.service.inquiry.InquiryService;
import com.booksphillic.service.inquiry.dto.GetInquiryRes;
import com.booksphillic.service.inquiry.dto.PostInquiryReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/inquiry")
@RestController
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;
    private final JwtService jwtService;
    private final AuthService authService;

    @GetMapping("/list/bookstore")
    public BaseResponse<List<GetInquiryRes>> getStoreInquiries(@RequestParam Long storeId,
                                                              @RequestParam(required = false) InquiryType type,
                                                              @RequestParam(defaultValue = "1") int page,
                                                              @RequestParam(defaultValue = "4") int size){
        try {
            List<GetInquiryRes> getInquiries;
            if(type == null) // 공개 문의만 조회
                getInquiries = inquiryService.getStorePublicInquiries(storeId, page, size);
            else
                getInquiries = inquiryService.getStoreInquiriesByType(storeId,type, page, size);

            return new BaseResponse<>(getInquiries);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }

    @GetMapping("/list/user")
    public BaseResponse<List<GetInquiryRes>> getUserInquiries(@RequestParam Long userId,
                                                              @RequestParam(defaultValue = "1") int page,
                                                              @RequestParam(defaultValue = "4") int size){
        try {
            List<GetInquiryRes> getInquiries;
            getInquiries = inquiryService.getUserInquiries(userId, page, size);

            return new BaseResponse<>(getInquiries);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }


    @PostMapping("")
    public BaseResponse<Long> postInquiry(@RequestHeader(name = "Authorization") String authorization,
                                          @RequestBody PostInquiryReq postInquiryReq) {
        try{
            String accessToken = authService.validateBearerToken(authorization);
            jwtService.validateJwt(accessToken, postInquiryReq.getInquirerId());

            Long inquiryId = inquiryService.createInquiry(postInquiryReq);
            return new BaseResponse<>(inquiryId);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }

    }

}
