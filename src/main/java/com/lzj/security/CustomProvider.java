package com.lzj.security;

import com.google.common.collect.Lists;
import com.lzj.domain.Account;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 这里调用CustomUserDetailServe进行身份验证
 */
public class CustomProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication cache = SecurityContextHolder.getContext().getAuthentication();
        if (cache != null && authentication instanceof AccountToken && cache instanceof AccountToken) {
            return cache;
        }

        AccountDetails userDetails = (AccountDetails) this.userDetailsService.loadUserByUsername((String) authentication.getPrincipal());
        if (userDetails.getAccount() == null) {
            return null;
        }
        if (!userDetails.getAccount().getPassword().equals(authentication.getCredentials())) {
            return null;
        }
        AccountToken token = (AccountToken) authentication;
        token.setAuthorities(Lists.newArrayList(new SimpleGrantedAuthority("friend_picture_group_see")));
        token.setAccount(userDetails.getAccount());
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
