package com.booksphillic.service.auth;

import com.booksphillic.domain.user.User;
import com.booksphillic.repository.UserRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.auth.dto.PostLoginRes;
import com.booksphillic.util.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final MailUtil mailUtil;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


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

    public void resetPassword(String email) throws BaseException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseCode.NON_EXISTENT_EMAIL));

        String tempPassword = UUID.randomUUID().toString().substring(0, 10);

        try {
            user.updatePassword(passwordEncoder.encode(tempPassword));
            userRepository.save(user);
            mailUtil.sendResetPasswordEmail(tempPassword, user);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }
}
