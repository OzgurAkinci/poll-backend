package com.app.pollbackend.config.credential;

import com.app.pollbackend.config.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ICredential {

    public CustomUserDetails getUserByUserName(String userName) throws UsernameNotFoundException;

}
