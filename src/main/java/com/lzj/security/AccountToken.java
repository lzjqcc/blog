package com.lzj.security;

import com.lzj.domain.Account;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class AccountToken implements Authentication, Serializable {
    public static long serialVersionUID = 5212581740984817395L;
    private Account  account;
    private  Object principal;
    private Object credentials;
    private Object details;
    private List<? extends GrantedAuthority> authorities;
    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public AccountToken(Collection<? extends GrantedAuthority> authorities) {
    }
    public AccountToken(Object principal, Object credentials , List<GrantedAuthority> grantedAuthorities) {
        this.principal = principal;
        this.credentials = credentials;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAuthorities(List<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getDetails() {
        return details;
    }
    public void setDetails(Object details) {
        this.details = details;
    }
    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }


    @Override
    public String getName() {
        return account.getEmail();
    }
}
