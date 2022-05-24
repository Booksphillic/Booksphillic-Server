package com.booksphillic.controller;

import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponse;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.auth.AuthService;
import com.booksphillic.service.auth.dto.PostLoginReq;
import com.booksphillic.service.auth.dto.PostLoginRes;
import com.booksphillic.service.auth.dto.ResetPasswordReq;
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

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final AuthService authService;

    private static final Pattern EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",Pattern.CASE_INSENSITIVE);

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
            PostLoginRes postLoginRes = authService.postLogin(email, password, passwordEncoder);
            return new BaseResponse<>(postLoginRes);
        }
        catch (BaseException e) {
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
}
