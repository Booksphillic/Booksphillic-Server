package com.booksphillic.domain.user;

import com.booksphillic.service.auth.dto.PostSignupReq;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", nullable = false)
    private UserRoleType roleType;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    private String password;

    @Column(length = 20)
    private String username;

    @Column(name = "phone_number", length = 11)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type", length = 10)
    @ColumnDefault("LOCAL")
    private ProviderType providerType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10)
    @Builder.Default
    private UserStatus userStatus = UserStatus.ACTIVE;

    @Column(name = "profile_img")
    private String profileImgUrl;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "terms_conditions")
    @ColumnDefault("true")
    private boolean termsConditions;

    @Column(name = "personal_data_processing_policy")
    @ColumnDefault("true")
    private boolean dataPolicy;

    @Column(name = "receive_email")
    @ColumnDefault("true")
    private boolean receiveEmail;

}
