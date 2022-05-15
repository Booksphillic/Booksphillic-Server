package com.booksphillic.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", nullable = false)
    private UserRoleType roleType;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(length = 20)
    private String password;

    @Column(length = 20)
    private String username;

    @Column(name = "phone_number", length = 11)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type", length = 10, nullable = false)
    private ProviderType providerType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10, nullable = false)
    private UserStatus userStatus;

    @Column(name = "profile_img", length = 50)
    private String profileImgUrl;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "terms_conditions", nullable = false)
    private boolean termsConditions;

    @Column(name = "personal_data_processing_policy", nullable = false)
    private boolean dataPolicy;

    @Column(name = "receive_email", nullable = false)
    private boolean receiveEmail;

}
