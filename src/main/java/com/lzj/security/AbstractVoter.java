package com.lzj.security;

import com.lzj.dao.FunctionDao;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;

public abstract class AbstractVoter implements AccessDecisionVoter<FilterInvocation> {
    protected WebExpressionVoter voter = new WebExpressionVoter();
    protected FunctionDao functionDao;
    public AbstractVoter(FunctionDao functionDao) {
        this.functionDao = functionDao;
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
