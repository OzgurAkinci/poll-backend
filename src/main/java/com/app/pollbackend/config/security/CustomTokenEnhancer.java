package com.app.pollbackend.config.security;

import com.app.pollbackend.domain.SUser;
import com.app.pollbackend.repository.SUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {
    @Autowired
    protected SUserDao userRepository;


    private List<TokenEnhancer> delegates = Collections.emptyList();

    public void setTokenEnhancers(List<TokenEnhancer> delegates) {
        this.delegates = delegates;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        return enhanceNew(userRepository, accessToken, authentication);
    }

    public OAuth2AccessToken enhanceNew(SUserDao userRepository, OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken tempResult = (DefaultOAuth2AccessToken) accessToken;

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getUserAuthentication().getPrincipal();
        SUser daoSUser = userRepository.findByUserName(userDetails.getUsername());
        final Map<String, Object> additionalInformation = new HashMap<String, Object>();
        additionalInformation.put("id", daoSUser.getId());
        additionalInformation.put("username", daoSUser.getUsername());
        additionalInformation.put("email", daoSUser.getEmail());
        additionalInformation.put("first_name", daoSUser.getName());
        additionalInformation.put("last_name", daoSUser.getSurname());
        //additionalInformation.put("permissions", providedFwDao.getUserDao().findPermissionsByUser(daoSUser.getId()));
        //List<String> roles = new ArrayList<String>();
        //for (Role role : providedFwDao.getUserRoleDao().findByUser(daoSUser.getId())) {
        //    roles.add(role.getRoleName());
        //}
        //additionalInformation.put("roles", roles.toArray());
        tempResult.setAdditionalInformation(additionalInformation);

        OAuth2AccessToken result = tempResult;
        for (TokenEnhancer enhancer : delegates) {
            result = enhancer.enhance(result, authentication);
        }
        return result;
    }
}
