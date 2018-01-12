package com.lzj.security;

import com.lzj.constant.URLMatchers;
import com.lzj.dao.FunctionDao;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

public class ConferenceVoter extends AbstractVoter {
    public ConferenceVoter(FunctionDao functionDao) {
        super(functionDao);
    }
    // 查看 get   更新 post
    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
        if (authentication instanceof AccountToken) {
            AccountToken accountToken = (AccountToken) authentication;
            if (object.getRequestUrl().contains(URLMatchers.CONFERENCE) && object.getHttpRequest().getMethod().equals(HttpMethod.GET.name())) {

            }
        }
        return 0;
    }
}
