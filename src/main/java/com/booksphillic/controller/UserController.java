package com.booksphillic.controller;

import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponse;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.auth.AuthService;
import com.booksphillic.service.auth.JwtService;
import com.booksphillic.service.user.UserService;
import com.booksphillic.service.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;
    private final JwtService jwtService;

    /**
     * 책방 스크랩하기
     */
    @PostMapping("/{storeId}/scrap")
    public BaseResponse<Boolean> postScrap(@PathVariable Long storeId, @RequestBody PostScrapReq postScrapReq,
                                           @RequestHeader(required = false, name = "authorization") String authorization) {
        try {
            String jwt = authService.validateBearerToken(authorization);
            jwtService.validateJwt(jwt, postScrapReq.getUserId());

            Long userId = postScrapReq.getUserId();
            boolean result = userService.postScrap(storeId, userId);
            return new BaseResponse(result);
        }
        catch(BaseException e) {
            return new BaseResponse(e.getCode());
        }
    }

    /**
     * 스크랩 조회
     */
    @GetMapping("/scrapList")
    public BaseResponse<List<GetScrapRes>> getScrap(@RequestParam Long userId,
                                                    @RequestHeader(required = false, name = "authorization") String authorization) {
        try {
            String jwt = authService.validateBearerToken(authorization);
            jwtService.validateJwt(jwt, userId);

            List<GetScrapRes> result = userService.getScrap(userId);
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse(e.getCode());
        }
    }

    /**
     * 이용자 마이페이지
     */
    // 메인 - 이름, 아이디, (미스터리북 신청 건수, 책방 리뷰 건수)
    // 미스터리북 신청 - 미스터리북 신청 내역
    // 프로필 설정 - (회원 이미지, 이름, 이메일(아이디), 비밀번호, 휴대폰 번호)
    // 스크랩 - 스크랩 내역(위의 scrapList api 사용)
    @GetMapping("/profile")
    public BaseResponse<GetProfileRes> getProfile(@RequestParam Long userId,
                                                  @RequestHeader(required = false, name = "authorization") String authorization) {
        try {
            String jwt = authService.validateBearerToken(authorization);
            jwtService.validateJwt(jwt, userId);

            GetProfileRes result = userService.getProfile(userId);
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse(e.getCode());
        }
    }

    // 프로필 이미지 등록
    @PostMapping("/profileImage")
    public BaseResponse<String> postProfileImage(@RequestParam MultipartFile file, @RequestParam(name = "userId") Long userId) {
        try {

            String url = userService.postProfileImage(file, userId);
            return new BaseResponse<>(url);
        } catch (BaseException e) {
            return new BaseResponse(e.getCode());
        }
    }

    // 프로필 이미지 삭제
    @PatchMapping("/profileImage")
    public BaseResponse deleteProfileImage(@RequestParam(name = "userId") Long userId) {
        try {

            userService.deleteProfileImage(userId);
            return new BaseResponse(BaseResponseCode.SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse(e.getCode());
        }
    }

    // 픽업, 리뷰 수 카운트
    @GetMapping("/pickupReviewCount")
    public BaseResponse<CountPickupReviewRes> countPickupReview(@RequestParam(name = "userId") Long userId,
                                                                @RequestHeader(required = false, name = "authorization") String authorization) {
        try {
            String jwt = authService.validateBearerToken(authorization);
            jwtService.validateJwt(jwt, userId);

            CountPickupReviewRes result = userService.countPickupReview(userId);
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse(e.getCode());
        }
    }

    // 내가 쓴 리뷰 조회
    @GetMapping("/getReview")
    public BaseResponse<List<GetReviewRes>> getReview(@RequestParam(name="userId") Long userId,
                                  @RequestHeader(required = false, name = "authorization") String authorization) {
        try {
            String jwt = authService.validateBearerToken(authorization);
            jwtService.validateJwt(jwt, userId);

            List<GetReviewRes> result = userService.getReview(userId);
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse(e.getCode());
        }
    }

}
