package com.lzj.security;

import com.google.common.collect.Lists;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 自定AcessDecisionManager就可以实现自定义角色名称
 */
public class CustomAccessDecisionManager implements AccessDecisionManager {
    private WebExpressionVoter voter = new WebExpressionVoter();
    private SecurityExpressionHandler<FilterInvocation> expressionHandler = new DefaultWebSecurityExpressionHandler();
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    /**
     *
     * @param authentication
     * @param object  访问url
     * @param configAttributes 角色
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        int i =0;
    /*    Iterator<ConfigAttribute> iterable = configAttributes.iterator();
        while (iterable.hasNext()) {


             i =0;
        }
        if (authentication instanceof AccountToken) {
            AccountToken token = (AccountToken) authentication;
        }else if (authentication instanceof AnonymousAuthenticationToken) {

        }*/
        int flag = voter.vote(authentication, (FilterInvocation) object, configAttributes);
        if (flag != AccessDecisionVoter.ACCESS_GRANTED) {
            throw new AccessDeniedException("没有访问权限");
        }
    }




    @Override
    public boolean supports(ConfigAttribute attribute) {
        // 这个是配置的属性
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
