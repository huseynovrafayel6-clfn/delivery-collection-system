package com.webperside.deliverycollectionsystem.filters;

import com.webperside.deliverycollectionsystem.exception.BaseException;
import com.webperside.deliverycollectionsystem.model.entity.User;
import com.webperside.deliverycollectionsystem.model.security.LoggedInUserDetails;
import com.webperside.deliverycollectionsystem.services.redis.RedisService;
import com.webperside.deliverycollectionsystem.services.user.UserService;
import com.webperside.deliverycollectionsystem.utils.PublicPrivateKeyUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private final PublicPrivateKeyUtils jwtUtils;
    private final UserService userService;
    private final RedisService redisService;

    public AuthorizationFilter(PublicPrivateKeyUtils jwtUtils,
                               UserService userService,
                               RedisService redisService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.redisService = redisService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null) {
                try {

                    Claims claims = jwtUtils.getClaimsFromJwtToken(jwt);
                    String tokenType = claims.get("type", String.class);

                    // Reject if token is a refresh token or blacklisted
                    if ("refresh".equals(tokenType)) {
                        log.warn("Attempt to use a refresh token as an access token.");
                        request.setAttribute("jwt_exception", new Exception("Attempt to use a refresh token as an access token."));
                    } else {

                        if (redisService.exists("access_token:" + jwt)) {

                            String username = claims.getSubject();

                            User user = userService.getById(Long.parseLong(username));
                            String email = user.getEmail();
                            String password = user.getPassword();

                            List<SimpleGrantedAuthority> authorities = userService.getRolesByEmail(email).stream()
                                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                                    .toList();

                            LoggedInUserDetails userDetails =
                                    new LoggedInUserDetails(email, password, authorities);

                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(
                                            userDetails,
                                            null,
                                            userDetails.getAuthorities());

                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        } else {
                            throw BaseException.unauthorized();
                        }
                    }

                } catch (ExpiredJwtException | BaseException e) {
                    log.warn("JWT token is expired: {}", e.getMessage());
                    request.setAttribute("jwt_exception", e);
                } catch (Exception e) {
                    log.error("JWT validation error: {}", e.getMessage());
                    request.setAttribute("jwt_exception", e);
                }
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
