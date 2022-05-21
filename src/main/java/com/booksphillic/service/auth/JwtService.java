package com.booksphillic.service.auth;

import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
@Slf4j
public class JwtService {

    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // access token 생성
    public String createAccessToken(Long userId) {
        Date now = new Date();
        int validMilliSeconds = 1000 * 60 * 60 * 24 * 14; // 2주 동안 유효

        return Jwts.builder()
                .setIssuedAt(now)
                .setSubject(userId.toString())
                .setExpiration(new Date(now.getTime() + validMilliSeconds))
                .signWith(key)
                .compact();
    }

    // 토큰 유효성 검사
    public void validateJwt(String jwt, Long userId) throws BaseException {
        Claims claims;
        try{
            claims = Jwts.parserBuilder()
                    .setSigningKey(key.getEncoded())
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.INVALID_ACCESS_TOKEN);
        }

        // 토큰이 만료된 경우
        if(claims.getExpiration().before(new Date())) {
            throw new BaseException(BaseResponseCode.EXPIRED_TOKEN);
        }

        // 다른 user의 id로 요청한 경우
        if(!claims.getSubject().equals(userId.toString())) {
            throw new BaseException(BaseResponseCode.UNAUTHORIZED_USER);
        }

    }

}
