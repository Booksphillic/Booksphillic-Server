package com.booksphillic.service.auth.dto;

import com.booksphillic.domain.user.UserRoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
//@AllArgsConstructor
@NoArgsConstructor
public class PostSignupReq {

    private String username;
    private String email;
    private String password;
    private String passwordCheck;
    private String phoneNumber;
    private UserRoleType roleType;

    // 사장님인 경우만
    private String registrationNumber;  // 사업자 등록번호
    private String storeName;           // 책방 이름

    // 동의 여부
    private boolean termsConditions;    // 이용약관
    private boolean dataPolicy;         // 개인정보처리방침
    private boolean receiveEmail;       // 이메일 수신 (선택)


}
