package com.app.pollbackend.config.security;


import com.app.pollbackend.domain.SUser;
import com.app.pollbackend.repository.SUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;


@Component
public class AccountStatusUserDetailsChecker implements UserDetailsChecker {
    @Autowired
    SUserDao userRepository;
    @Override
    public void check(UserDetails user) {
        SUser myUser = userRepository.findByUserName(user.getUsername());
        if (!user.isAccountNonLocked()) {
            throw new LockedException("LockedException");
        }

        if (!user.isEnabled()) {
            throw new DisabledException("DisabledException");
        }

        if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("AccountExpiredException");
        }

        if (!user.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("CredentialsExpiredException");
        }
    }
}
