package com.example.demooauth.config;

import com.example.demooauth.model.JdbcAccessToken;
import com.example.demooauth.model.JdbcRefreshToken;
import com.example.demooauth.repository.JdbcAccessTokenRepository;
import com.example.demooauth.repository.JdbcRefreshTokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class JdbcTokenStore implements TokenStore {

    private JdbcAccessTokenRepository jdbcAccessTokenRepository;
    private JdbcRefreshTokenRepository jdbcRefreshTokenRepository;

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    public JdbcTokenStore(JdbcAccessTokenRepository jdbcAccessTokenRepository,
                          JdbcRefreshTokenRepository jdbcRefreshTokenRepository) {
        this.jdbcAccessTokenRepository = jdbcAccessTokenRepository;
        this.jdbcRefreshTokenRepository = jdbcRefreshTokenRepository;
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken accessToken) {
        return readAuthentication(accessToken.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String tokenValue) {
        Optional<JdbcAccessToken> accessToken = jdbcAccessTokenRepository.findByTokenId(
                extractTokenKey(tokenValue));

        if (accessToken.isPresent()) {
            return accessToken.get().getAuthentication();
        }

        return null;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String refreshToken = null;

        if (accessToken.getRefreshToken() != null) {
            refreshToken = accessToken.getRefreshToken().getValue();
        }

        if (readAccessToken(accessToken.getValue()) != null) {
            this.removeAccessToken(accessToken);
        }

        JdbcAccessToken jdbcAccessToken = new JdbcAccessToken();
        jdbcAccessToken.setId(UUID.randomUUID().toString() + UUID.randomUUID().toString());
        jdbcAccessToken.setTokenId(extractTokenKey(accessToken.getValue()));
        jdbcAccessToken.setToken(accessToken.getValue());
        jdbcAccessToken.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
        jdbcAccessToken.setUsername(authentication.isClientOnly() ? null : authentication.getName());
        jdbcAccessToken.setClientId(authentication.getOAuth2Request().getClientId());
        jdbcAccessToken.setAuthentication(authentication);
        jdbcAccessToken.setRefreshToken(extractTokenKey(refreshToken));

        jdbcAccessTokenRepository.save(jdbcAccessToken);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        Optional<JdbcAccessToken> accessToken = jdbcAccessTokenRepository.findByTokenId(
                extractTokenKey(tokenValue));

        if (accessToken.isPresent()) {
            return new DefaultOAuth2AccessToken(accessToken.get().getToken());
        }

        return null;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken oAuth2AccessToken) {
        Optional<JdbcAccessToken> accessToken = jdbcAccessTokenRepository.findByTokenId(
                extractTokenKey(oAuth2AccessToken.getValue()));

        if (accessToken.isPresent()) {
            jdbcAccessTokenRepository.delete(accessToken.get());
        }
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        JdbcRefreshToken jdbcRefreshToken = new JdbcRefreshToken();
        jdbcRefreshToken.setId(UUID.randomUUID().toString() + UUID.randomUUID().toString());
        jdbcRefreshToken.setTokenId(extractTokenKey(refreshToken.getValue()));
        jdbcRefreshToken.setToken(refreshToken.getValue());
        jdbcRefreshToken.setAuthentication(authentication);

        jdbcRefreshTokenRepository.save(jdbcRefreshToken);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        Optional<JdbcRefreshToken> refreshToken = jdbcRefreshTokenRepository.findByTokenId(
                extractTokenKey(tokenValue));

        return refreshToken.isPresent() ? new DefaultOAuth2RefreshToken(refreshToken.get().getToken()) : null;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken refreshToken) {
        Optional<JdbcRefreshToken> jdbcRefreshToken = jdbcRefreshTokenRepository.findByTokenId(
                extractTokenKey(refreshToken.getValue()));

        return jdbcRefreshToken.isPresent() ? jdbcRefreshToken.get().getAuthentication() : null;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken refreshToken) {
        Optional<JdbcRefreshToken> jdbcRefreshToken = jdbcRefreshTokenRepository.findByTokenId(
                extractTokenKey(refreshToken.getValue()));

        if (jdbcRefreshToken.isPresent()) {
            jdbcRefreshTokenRepository.delete(jdbcRefreshToken.get());
        }
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        Optional<JdbcAccessToken> jdbcAccessToken = jdbcAccessTokenRepository.findByRefreshToken(
                extractTokenKey(refreshToken.getValue()));

        if (jdbcAccessToken.isPresent()) {
            jdbcAccessTokenRepository.delete(jdbcAccessToken.get());
        }
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken accessToken = null;
        String authenticationId = authenticationKeyGenerator.extractKey(authentication);
        Optional<JdbcAccessToken> token = jdbcAccessTokenRepository.findByAuthenticationId(
                authenticationId);

        if (token.isPresent()) {
            accessToken = new DefaultOAuth2AccessToken(token.get().getToken());

            if (accessToken != null && !authenticationId.equals(this.authenticationKeyGenerator.extractKey(
                    this.readAuthentication(accessToken)))) {
                this.removeAccessToken(accessToken);
                this.storeAccessToken(accessToken, authentication);
            }
        }

        return accessToken;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String cliendId, String username) {
        Collection<OAuth2AccessToken> tokens = new ArrayList<>();
        List<JdbcAccessToken> result = jdbcAccessTokenRepository.findByClientIdAndUsername(cliendId, username);

        result.forEach(e ->  tokens.add(new DefaultOAuth2AccessToken(e.getToken())));

        return tokens;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        Collection<OAuth2AccessToken> tokens = new ArrayList<>();
        List<JdbcAccessToken> result = jdbcAccessTokenRepository.findByClientId(clientId);

        result.forEach(e -> tokens.add(new DefaultOAuth2AccessToken(e.getToken())));

        return tokens;
    }

    private String extractTokenKey(String value) {
        if (value == null) {
            return null;
        } else {
            MessageDigest digest;

            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException("MD5 algorithm not available. Fatal (should be in the JDK).");
            }

            try {
                byte[] e = digest.digest(value.getBytes("UTF-8"));
                return String.format("%032x", new Object[]{new BigInteger(1, e)});
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException("UTF-8 encoding not available. Fatal (should be in the JDK).");
            }
        }
    }
}
