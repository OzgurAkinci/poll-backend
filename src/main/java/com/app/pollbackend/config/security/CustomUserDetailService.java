package com.app.pollbackend.config.security;

import com.app.pollbackend.config.credential.ICredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    @Qualifier("DBCredential")
    ICredential credential;

    @Override
    @Transactional()
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return credential.getUserByUserName(username);
    }
}
