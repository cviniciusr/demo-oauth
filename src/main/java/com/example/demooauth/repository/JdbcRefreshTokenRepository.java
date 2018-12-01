package com.example.demooauth.repository;

import com.example.demooauth.model.JdbcRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JdbcRefreshTokenRepository extends JpaRepository<JdbcRefreshToken, String> {

    Optional<JdbcRefreshToken> findByTokenId(String tokenId);
}
