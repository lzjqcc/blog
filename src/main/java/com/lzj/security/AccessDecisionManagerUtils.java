package com.lzj.security;

import com.google.common.collect.Lists;
import com.lzj.dao.BaseDao;
import com.lzj.dao.FriendDao;
import com.lzj.dao.dto.FriendDto;
import com.lzj.domain.BaseEntity;
import com.lzj.domain.Function;
import com.lzj.utils.JsonUtils;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;
import java.util.List;

public class AccessDecisionManagerUtils {
    public static void  decide(AccessDecisionVoter voter,
                                Authentication authentication,
                               Object object,
                               Collection<ConfigAttribute> configAttributes,
                               List<Function> list) {

        AccountToken token = (AccountToken) authentication;
        List<SimpleGrantedAuthority> authorities = Lists.newArrayList();
        for (Function function : list) {
            authorities.add(new SimpleGrantedAuthority(function.getAuthority()));
        }
        token.setAuthorities(authorities);
        int flag = voter.vote(token, (FilterInvocation) object, configAttributes);
        if (flag != AccessDecisionVoter.ACCESS_GRANTED) {
            token.setAuthorities(null);
            throw new AccessDeniedException("没有访问权限");
        }
        token.setAuthorities(null);
    }
}
