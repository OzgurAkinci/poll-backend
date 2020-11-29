package com.app.pollbackend.config.credential;


import com.app.pollbackend.config.security.CustomUserDetails;
import com.app.pollbackend.domain.SUser;
import com.app.pollbackend.repository.SUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DBCredential implements ICredential {
    @Autowired
    SUserDao userRepository;

    @Override
    public CustomUserDetails getUserByUserName(String userName) throws UsernameNotFoundException {
        SUser myUser = userRepository.findByUserName(userName);
        if (myUser == null) throw new UsernameNotFoundException(userName);

        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        CustomUserDetails user = new CustomUserDetails(myUser,new ArrayList<>(myUser.getRoles()), accountNonExpired, credentialsNonExpired, accountNonLocked);
        return user;
    }
}
