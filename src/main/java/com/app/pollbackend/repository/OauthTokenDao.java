package com.app.pollbackend.repository;

import com.app.pollbackend.domain.OauthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface OauthTokenDao extends JpaRepository<OauthToken, String> {
    List<OauthToken> findAllByClientIdAndUsername(@Param("clientId") String clientId, @Param("username") String username);

    List<OauthToken> findAllByClientId(@Param("clientId") String clientId);

    Optional<OauthToken> findByTokenId(@Param("tokenId") String tokenId);

}
