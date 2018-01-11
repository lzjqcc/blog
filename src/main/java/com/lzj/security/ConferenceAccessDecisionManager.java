package com.lzj.security;

import com.lzj.dao.ConferenceDao;
import com.lzj.dao.dto.ConferenceDto;
import com.lzj.domain.Function;
import com.lzj.utils.JsonUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Collection;
import java.util.List;

/**
 * 会议权限
 */
public class ConferenceAccessDecisionManager implements AccessDecisionManager {
    private ConferenceDao conferenceDao;
    private WebExpressionVoter voter = new WebExpressionVoter();

    public ConferenceAccessDecisionManager(ConferenceDao conferenceDao) {
        this.conferenceDao = conferenceDao;
    }
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        AccountToken token = (AccountToken) authentication;
        FilterInvocation invocation = (FilterInvocation) object;
        ConferenceDto dto = JsonUtils.requestToObject(invocation.getRequest(), ConferenceDto.class);
        dto.setCurrentAccountId(token.getAccount().getId());
        List<Function> list = conferenceDao.findConferenceFunction(dto);
        AccessDecisionManagerUtils.decide(voter,authentication,object,configAttributes,list);
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
