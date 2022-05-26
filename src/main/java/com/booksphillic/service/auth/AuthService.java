package com.booksphillic.service.auth;

import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.user.ProviderType;
import com.booksphillic.domain.user.User;
import com.booksphillic.domain.user.UserRoleType;
import com.booksphillic.repository.UserRepository;
import com.booksphillic.repository.bookstore.BookstoreRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.auth.dto.PostLoginRes;

import com.booksphillic.util.MailUtil;
import lombok.RequiredArgsConstructor;
import com.booksphillic.service.auth.dto.PostSignupReq;
import com.booksphillic.service.auth.dto.PostSignupRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final BookstoreRepository bookstoreRepository;
    private final JwtService jwtService;
    private final MailUtil mailUtil;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    // 로그인
    public PostLoginRes postLogin(String email, String password) throws BaseException {

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

    // 회원가입
    @Transactional
    public PostSignupRes postSignup(PostSignupReq postSignupReq) throws BaseException {

        Optional<User> user;
        try {
            user = userRepository.findByEmail(postSignupReq.getEmail());
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }

        // 이미 이메일이 존재하는 경우
        if(user.isPresent()) {
            throw new BaseException(BaseResponseCode.DUPLICATED_EMAIL);
        }

        try{
            String encodedPassword = passwordEncoder.encode(postSignupReq.getPassword());
            User newUser = User.builder()
                    .providerType(ProviderType.LOCAL)
                    .email(postSignupReq.getEmail())
                    .password(encodedPassword)
                    .username(postSignupReq.getUsername())
                    .phoneNumber(postSignupReq.getPhoneNumber())
                    .roleType(postSignupReq.getRoleType())
                    .dataPolicy(postSignupReq.isDataPolicy())
                    .termsConditions(postSignupReq.isTermsConditions())
                    .receiveEmail(postSignupReq.isReceiveEmail()).build();
            User savedUser = userRepository.save(newUser);

            // 사장님인 경우, 서점도 등록
            if(postSignupReq.getRoleType().equals(UserRoleType.OWNER)) {
                Bookstore newStore = Bookstore.builder()
                                                .name(postSignupReq.getStoreName())
                                                .registrationNumber(postSignupReq.getRegistrationNumber())
                                                .user(savedUser).build();
                bookstoreRepository.createBookstore(newStore);
            }

            return PostSignupRes.builder().userId(newUser.getId()).build();

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }
}
