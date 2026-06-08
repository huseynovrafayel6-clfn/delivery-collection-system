package com.webperside.deliverycollectionsystem.services.token;

import com.webperside.deliverycollectionsystem.model.entity.User;
import com.webperside.deliverycollectionsystem.model.properties.security.SecurityProperties;
import com.webperside.deliverycollectionsystem.services.base.TokenGenerator;
import com.webperside.deliverycollectionsystem.services.base.TokenReader;
import com.webperside.deliverycollectionsystem.services.getters.EmailGetter;
import com.webperside.deliverycollectionsystem.services.redis.RedisService;
import com.webperside.deliverycollectionsystem.utils.PublicPrivateKeyUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.webperside.deliverycollectionsystem.constants.token.TokenConstants.EMAIL_KEY;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccessTokenManager implements TokenGenerator<User>,
        TokenReader<Claims>, EmailGetter {

    private final SecurityProperties securityProperties;
    private final RedisService redisService;

    @Override
    public String generate(User obj) {

        Claims claims = Jwts.claims();
        claims.put(EMAIL_KEY, obj.getEmail());

        Date now = new Date();
        Date exp = new Date(now.getTime() + securityProperties.getJwt().getAccessTokenValidityTime());

        String token = Jwts.builder()
                .setSubject(String.valueOf(obj.getId()))
                .setIssuedAt(now)
                .setExpiration(exp)
                .addClaims(claims)
                .signWith(PublicPrivateKeyUtils.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();

        redisService.save(
                "access_token:" + token,
                obj.getId(),
                exp.getTime() - System.currentTimeMillis(),
                TimeUnit.MILLISECONDS
        );

        return token;
    }

    @Override
    public Claims read(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(PublicPrivateKeyUtils.getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String getEmail(String token) {
        return this.read(token).get(EMAIL_KEY, String.class);
    }
}