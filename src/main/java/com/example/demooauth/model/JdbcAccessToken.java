package com.example.demooauth.model;

import com.example.demooauth.utils.SerializableObjectConverter;
import lombok.Data;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.persistence.*;

@Entity
@Table(name = "jdbc_access_token")
@Data
public class JdbcAccessToken {

    @Id
    private String id;
    private String tokenId;
//    @Transient
//    private OAuth2AccessToken token;
    private String token;
    private String authenticationId;
    private String username;
    private String clientId;
    @Column(columnDefinition = "text", length = 65535)
    private String authentication;
    private String refreshToken;


    public OAuth2Authentication getAuthentication() {
        return SerializableObjectConverter.deserialize(authentication);
    }

    public void setAuthentication(OAuth2Authentication authentication) {
        this.authentication = SerializableObjectConverter.serialize(authentication);
    }

    public String getAuthentication2() {
        return authentication;
    }
}
