package com.example.demooauth.model;

import com.example.demooauth.utils.SerializableObjectConverter;
import lombok.Data;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.persistence.*;

@Entity
@Table(name = "jdbc_refresh_token")
@Data
public class JdbcRefreshToken {

    @Id
    private String id;
    private String tokenId;
//    @Transient
//    private OAuth2RefreshToken token;
    private String token;
    @Column(columnDefinition = "text", length = 65535)
    private String authentication;


    public OAuth2Authentication getAuthentication() {
        return SerializableObjectConverter.deserialize(authentication);
    }

    public void setAuthentication(OAuth2Authentication authentication) {
        this.authentication = SerializableObjectConverter.serialize(authentication);
    }
}
