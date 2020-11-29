package com.app.pollbackend.domain;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "oauth_access_token")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Data
public class OauthToken implements Serializable {
    @Id
    @Column(name = "authentication_id")
    private String authenticationId;

    @Column(name = "token_id")
    private String tokenId;

    @Column(name = "token")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] token;

    @Column(name = "user_name")
    private String username;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "authentication")
    private OAuth2Authentication authentication;

    @Column(name = "refresh_token")
    private String refreshToken;

}
