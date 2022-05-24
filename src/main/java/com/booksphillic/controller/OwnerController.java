package com.booksphillic.controller;

import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponse;
import com.booksphillic.service.user.OwnerService;
import com.booksphillic.service.user.dto.GetOwnerProfileRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/owner")
public class OwnerController {

    private final OwnerService ownerService;

    /**
     * 사장님 마이페이지
     */
    // 내 책방 관리
    @GetMapping("/profile")
    public BaseResponse<GetOwnerProfileRes> getStoreProfile(@RequestParam(name = "userId") Long userId) {
        try {
            GetOwnerProfileRes result = ownerService.getStoreProfile(userId);
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }
}
