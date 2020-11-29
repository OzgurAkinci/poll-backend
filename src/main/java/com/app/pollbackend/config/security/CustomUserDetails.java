package com.app.pollbackend.config.security;


import com.app.pollbackend.domain.SRole;
import com.app.pollbackend.domain.SUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String username;
    private Boolean accountNonExpired;
    private Boolean enabled;
    private Boolean credentialsNonExpired;
    private Boolean accountNonLocked;


    public CustomUserDetails(SUser SUser, List<SRole> roles, Boolean accountNonExpired, Boolean credentialsNonExpired, Boolean accountNonLocked) {
        this.username = SUser.getUsername();
        this.password = SUser.getPassword();
        this.authorities = translate(roles);
        this.enabled = true;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
    }

    private Collection<? extends GrantedAuthority> translate(List<SRole> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (SRole role : roles) {
            String name = role.getRoleName().toUpperCase();
            if (!name.startsWith("ROLE_")) {
                name = "ROLE_" + name;
            }
            authorities.add(new SimpleGrantedAuthority(name));
        }
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }


    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
