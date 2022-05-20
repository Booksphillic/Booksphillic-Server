package com.booksphillic.service.auth;

import com.booksphillic.domain.user.User;
import com.booksphillic.repository.UserRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.auth.dto.PostLoginRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;


    public PostLoginRes postLogin(String email, String password, PasswordEncoder passwordEncoder) throws BaseException {

        User user;
        try{
            user = userRepository.findByEmail(email).orElse(null);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }

        // 존재하지 않는 이메일
        if(user == null) {
            throw new BaseException(BaseResponseCode.NON_EXISTENT_EMAIL);
        }

        // 올바르지 않은 비밀번호
        if(!(passwordEncoder.matches(password, user.getPassword()))) {
            throw new BaseException(BaseResponseCode.WRONG_PASSWORD);
        }

        // access token 생성
        String accessToken = jwtService.createAccessToken(user.getId());

        return PostLoginRes.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .build();

    }

    // access token 추출
    public String validateBearerToken(String authorization) throws BaseException {
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            throw new BaseException(BaseResponseCode.EMPTY_ACCESS_TOKEN);
        }
        return authorization.substring(7); // "Bearer " 이후부터 반환
    }
}
