package com.lzj.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationManager implements AuthenticationManager {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.print(authentication);
        return authentication;
    }
}
