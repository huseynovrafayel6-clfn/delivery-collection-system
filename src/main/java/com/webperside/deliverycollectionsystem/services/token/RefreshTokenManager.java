package com.webperside.deliverycollectionsystem.services.token;

import com.webperside.deliverycollectionsystem.exception.BaseException;
import com.webperside.deliverycollectionsystem.model.dto.RefreshTokenDto;
import com.webperside.deliverycollectionsystem.model.entity.RefreshToken;
import com.webperside.deliverycollectionsystem.model.entity.User;
import com.webperside.deliverycollectionsystem.model.properties.security.SecurityProperties;
import com.webperside.deliverycollectionsystem.repository.RefreshTokenRepository;
import com.webperside.deliverycollectionsystem.services.base.TokenGenerator;
import com.webperside.deliverycollectionsystem.services.base.TokenReader;
import com.webperside.deliverycollectionsystem.services.getters.EmailGetter;
import com.webperside.deliverycollectionsystem.utils.PublicPrivateKeyUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static com.webperside.deliverycollectionsystem.constants.token.TokenConstants.EMAIL_KEY;
import static com.webperside.deliverycollectionsystem.model.enums.response.ErrorResponseMessages.ACCESS_TOKEN_NOT_ALLOWED;

@Component
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenManager implements TokenGenerator<RefreshTokenDto>,
        TokenReader<Claims>, EmailGetter {

    private final SecurityProperties securityProperties;
    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    public String generate(RefreshTokenDto obj) {

        final User user = obj.getUser();

        Claims claims = Jwts.claims();
        claims.put(EMAIL_KEY, user.getEmail());
        claims.put("type", "REFRESH_TOKEN");

        Date now = new Date();
        Date exp = new Date(now.getTime() + securityProperties.getJwt().getRefreshTokenValidityTime(obj.isRememberMe()));

        LocalDateTime expiresAt = LocalDateTime.ofInstant(
                exp.toInstant(),
                ZoneId.systemDefault()
        );

        String token = Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(now)
                .setExpiration(exp)
                .addClaims(claims)
                .signWith(PublicPrivateKeyUtils.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();

        refreshTokenRepository.save(RefreshToken.builder()
                .token(token)
                .expiresAt(expiresAt)
                .user(user)
                .build());

        return token;
    }

    @Override
    public Claims read(String token) {
        Claims tokenData = Jwts.parserBuilder()
                .setSigningKey(PublicPrivateKeyUtils.getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String typeOfToken = tokenData.get("type", String.class);

        if (!"REFRESH_TOKEN".equals(typeOfToken)) {
            throw BaseException.of(ACCESS_TOKEN_NOT_ALLOWED);
        }

        return tokenData;
    }

    @Override
    public String getEmail(String token) {
        return read(token).get(EMAIL_KEY, String.class);
    }
}
