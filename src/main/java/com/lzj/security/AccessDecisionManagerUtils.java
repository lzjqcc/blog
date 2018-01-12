package com.lzj.security;

import com.google.common.collect.Lists;
import com.lzj.dao.FriendDao;
import com.lzj.dao.dto.FriendDto;
import com.lzj.domain.Account;
import com.lzj.domain.BaseEntity;
import com.lzj.domain.Function;
import com.lzj.utils.JsonUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

public class AccessDecisionManagerUtils {
    public static int  decide(AccessDecisionVoter voter,
                                Authentication authentication,
                               Object object,
                               Collection<ConfigAttribute> configAttributes,
                               List<Function> list) {
        if (authentication instanceof  AccountToken && list != null) {

            AccountToken token = (AccountToken) authentication;

            List<SimpleGrantedAuthority> authorities = Lists.newArrayList();
            for (Function function : list) {
                authorities.add(new SimpleGrantedAuthority(function.getAuthority()));
            }
            token.setAuthorities(authorities);
            authentication = token;
            return voter.vote(token, object, configAttributes);
        }
        return voter.vote(authentication, (FilterInvocation) object, configAttributes);

    }
    public static <T> T getParamFromRequest(HttpServletRequest request,T object) {
        if (request.getMethod().equals(HttpMethod.POST)) {
            return JsonUtils.requestToObject(request, (Class<T>) object.getClass());
        }else if (request.getMethod().equals(HttpMethod.GET)) {
            return (T) request.getParameter((String) object);
        }
        return null;
    }
    public static void main(String [] args) {
        Account account = new Account();
        get(account);
    }
    public static <T> T get(T t) {
        System.out.println((Class<T>)t.getClass());
        return t;
    }
}
