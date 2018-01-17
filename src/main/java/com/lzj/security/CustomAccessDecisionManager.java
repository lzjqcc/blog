package com.lzj.security;

import com.google.common.collect.Lists;
import com.lzj.VO.ResponseVO;
import com.lzj.constant.URLMatchers;
import com.lzj.dao.ConferenceDao;
import com.lzj.dao.FriendDao;
import com.lzj.dao.FunctionDao;
import com.lzj.dao.dto.ConferenceDto;
import com.lzj.dao.dto.FriendDto;
import com.lzj.domain.Function;
import com.lzj.service.impl.FriendService;
import com.lzj.utils.JsonUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
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
import org.springframework.security.core.AuthenticationException;
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
    private SecurityExpressionHandler<FilterInvocation> expressionHandler = new DefaultWebSecurityExpressionHandler();
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private List<AccessDecisionVoter<FilterInvocation>> voters = null;
    public void setVoters(List<AccessDecisionVoter<FilterInvocation>> voters) {
        this.voters = voters;
    }

    /**
     *权限认证完成后清除权限
     * @param authentication
     * @param object  访问url
     * @param configAttributes 角色
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        boolean flag = false;
        for (AccessDecisionVoter<FilterInvocation> decisionVoter : voters) {
            int i = decisionVoter.vote(authentication, (FilterInvocation) object, configAttributes);
            switch (i) {
                case AccessDecisionVoter.ACCESS_GRANTED:
                    flag = true;
                    break;
            }

        }
        if (!flag && authentication instanceof AnonymousAuthenticationToken) {
            throw new InsufficientAuthenticationException("匿名用户无法访问");
        }
        if (!flag && authentication instanceof AccountToken) {
            throw new AccessDeniedException("没有权限访问");
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
