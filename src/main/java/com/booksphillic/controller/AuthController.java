package com.booksphillic.controller;

import com.booksphillic.domain.user.UserRoleType;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponse;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.auth.AuthService;
import com.booksphillic.service.auth.dto.PostLoginReq;
import com.booksphillic.service.auth.dto.PostLoginRes;
import com.booksphillic.service.auth.dto.ResetPasswordReq;
import com.booksphillic.service.auth.dto.PostSignupReq;
import com.booksphillic.service.auth.dto.PostSignupRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    private static final Pattern EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",Pattern.CASE_INSENSITIVE);
    private static final Pattern PHONE_NUMBER = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");

    /***
     * 로그인
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> postLogin(@RequestBody PostLoginReq postLoginReq) {
        String email = postLoginReq.getEmail();
        String password = postLoginReq.getPassword();

        // 이메일 형식 검사
        if(!isRegexEmail(email)) {
            return new BaseResponse<>(BaseResponseCode.INVALID_FORMATTED_EMAIL);
        }

        try {
            PostLoginRes postLoginRes = authService.postLogin(email, password);
            return new BaseResponse<>(postLoginRes);
        }
        catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }


    }


    /***
     * 회원 가입
     */
    @PostMapping("/signup")
    @ResponseBody
    public BaseResponse<PostSignupRes> postSignup(@RequestBody PostSignupReq postSignupReq) {

        // 비밀번호 확인 불일치
        if(!postSignupReq.getPassword().equals(postSignupReq.getPasswordCheck())) {
            return new BaseResponse<>(BaseResponseCode.PASSWORD_CONFIRMATION_DIFFERENT);
        }

        // 이메일 형식 검사
        if(!isRegexEmail(postSignupReq.getEmail())) {
            return new BaseResponse<>(BaseResponseCode.INVALID_FORMATTED_EMAIL);
        }

        // 휴대폰번호 형식 검사
        if(!isRegexPhoneNumber(postSignupReq.getPhoneNumber())) {
            return new BaseResponse<>(BaseResponseCode.INVALID_FORMATTED_PHONE_NUMBER);
        }

        // 필수 항목 미동의
        if(!postSignupReq.isDataPolicy() || !postSignupReq.isTermsConditions())
            return new BaseResponse<>(BaseResponseCode.DISAGREE_REQUIRED_ITEMS);


        try {
            PostSignupRes postSignupRes = authService.postSignup(postSignupReq);
            return new BaseResponse<>(postSignupRes);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }


    // 이메일 형식 검사
    private boolean isRegexEmail(String email) {
        return EMAIL.matcher(email).find();
    }

    // 비밀번호 찾기
    @PostMapping("/reset-password")
    public BaseResponse<Void> resetPassword(@RequestBody ResetPasswordReq resetPasswordReq) {
        try {
            authService.resetPassword(resetPasswordReq.getEmail());
            return new BaseResponse<>(BaseResponseCode.SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }

    // 휴대폰번호 형식 검사
    private boolean isRegexPhoneNumber(String phoneNumber) {
        return PHONE_NUMBER.matcher(phoneNumber).find();
    }
}
