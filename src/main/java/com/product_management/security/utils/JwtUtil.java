package com.product_management.security.utils;

import com.product_management.security.config.SecurityConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.product_management.constants.AppConstants.AUTH_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final SecurityConfigProperties configurationProperties;

    /**
     * Extracts a claim from the JWT token.
     *
     * @param token the JWT token
     * @param claimsResolver the function to extract the claim
     * @param <T> the type of the claim to be extracted
     * @return the extracted claim
     */
    public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates an access token for the specified username.
     *
     * @param username the username for which to generate the access token
     * @return the generated access token
     */
    public String generateAccessToken(final String username) {
        return buildToken(new HashMap<>(), username, configurationProperties.getAccessTokenExpiry());
    }

    /**
     * Generates a refresh token for the specified username.
     *
     * @param username the username for which to generate the refresh token
     * @return the generated refresh token
     */
    public String generateRefreshToken(final String username) {
        return buildToken(new HashMap<>(), username, configurationProperties.getRefreshTokenExpiry());
    }

    private String buildToken(
            final Map<String, Object> extraClaims,
            final String username,
            final long expiration
    ) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean validateToken(final String token) {
        try {
            return isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }


    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(final String token) {
        return !extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(configurationProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Retrieves the JWT token from the HTTP request header.
     *
     * @param request the HTTP request containing the token in the Authorization header
     * @return the JWT token if present, or null if the token is not found
     */
    public String getJWTFromRequest(final HttpServletRequest request) {
        var bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AUTH_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }


}
