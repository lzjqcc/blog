package com.lzj.security;

import com.lzj.domain.Account;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class AccountToken extends AbstractAuthenticationToken {
    private Account  account;
    private  Object principal;
    private Object credentials;
    private List<GrantedAuthority> grantedAuthorities;
    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public AccountToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }
    public AccountToken(Object principal, Object credentials , List<GrantedAuthority> grantedAuthorities) {
        super(grantedAuthorities);
        this.principal = principal;
        this.credentials = credentials;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
