package com.pwang.projoect.jwt;

import com.pwang.projoect.domain.user.entity.CustumUserDetail;
import com.pwang.projoect.domain.user.service.CustumUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static String secretKey = "!@pwangaccesstoken";
    private static String refreshSecretKey = "!@pwangrefresh";

    private static long tokenValidTime = 60 * 60 * 2 * 1000L;

    private static long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 8 * 1000L;

    private final CustumUserDetailsService custumUserDetailsService;

    public static String createAccessToken(String userPk, String roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + tokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public static String createJwtRefreshToken(String value) {
        Claims claims = Jwts.claims();
        claims.put("value", value);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS256, refreshSecretKey)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        CustumUserDetail userDetails = (CustumUserDetail) custumUserDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

//    public Authentication getAuthenticationRefreshToken(String refreshToken) {
//        User user = user.findwithrefreshToken(getClaimsRefresh(refreshToken).get("value").toString()).orElseThrow(() -> new BusinessExceptionHandler("????????? ???????????? ????????????.", ErrorCode.ENTITY_NOT_FOUND));
//        UserDetails userDetails = userDetailsService.loadUserByUsername(api.getApiId());
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//    }

    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        return request.getHeader("RefreshToken");
    }

    public boolean validateToken(ServletRequest request, String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return true;
        } catch (MalformedJwtException e) {
            request.setAttribute("exception", "MalformedJwtException");
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", "ExpiredJwtException");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", "UnsupportedJwtException");
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", "IllegalArgumentException");
        } catch (SignatureException e) {
            request.setAttribute("exception", "SignatureException");
        }
        return false;
    }

    public boolean validateRefreshToken(ServletRequest request, String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(jwtToken);
            return true;
        } catch (MalformedJwtException e) {
            request.setAttribute("exception", "MalformedJwtException");
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", "ExpiredJwtException");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", "UnsupportedJwtException");
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", "IllegalArgumentException");
        } catch (SignatureException e) {
            request.setAttribute("exception", "SignatureException");
        }
        return false;
    }

    public static Claims getClaimsRefresh(String jwtToken) throws JwtException {
        return Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(jwtToken).getBody();
    }
}