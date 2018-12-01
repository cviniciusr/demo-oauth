package com.example.demooauth.repository;

import com.example.demooauth.model.JdbcAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JdbcAccessTokenRepository extends JpaRepository<JdbcAccessToken, String> {

    List<JdbcAccessToken> findByClientId(String clientId);

    List<JdbcAccessToken> findByClientIdAndUsername(String clientId, String username);

    Optional<JdbcAccessToken> findByTokenId(String tokenId);

    Optional<JdbcAccessToken> findByRefreshToken(String refreshToken);

    Optional<JdbcAccessToken> findByAuthenticationId(String authenticationId);
}
