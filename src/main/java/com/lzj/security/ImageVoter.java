package com.lzj.security;

import com.google.common.collect.Lists;
import com.lzj.constant.URLMatchers;
import com.lzj.dao.FunctionDao;
import com.lzj.domain.Function;
import com.lzj.exception.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ImageVoter extends AbstractVoter {
    public ImageVoter(FunctionDao functionDao) {
        super(functionDao);
    }
    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
        // 查看相册
        if (object.getRequestUrl().contains(URLMatchers.PICTURE) && object.getHttpRequest().getMethod().equals(HttpMethod.GET.name())) {
            if (authentication instanceof AccountToken) {
                AccountToken accountToken = (AccountToken) authentication;
                String friendId = object.getHttpRequest().getParameter("friendId");
                if (StringUtils.isEmpty(friendId)) {
                    throw new BusinessException(305, "缺少参数friendId");
                }
                return AccessDecisionManagerUtils.decide(voter, authentication, object, attributes, getFunctions(accountToken,friendId));
            }
            //评论相册
        }else if (object.getRequestUrl().contains(URLMatchers.PICTURE) && object.getHttpRequest().getMethod().equals(HttpMethod.POST.name())) {
            if (authentication instanceof AccountToken) {

            }
        }

        return voter.vote(authentication,object,attributes);
    }
    private List<Function> getFunctions(AccountToken accountToken, String friendId) {
        List<Function> groupFunctions = functionDao.findGroupFunction(accountToken.getAccount().getId(),Integer.parseInt(friendId));
        List<Function> friendFunctions = functionDao.findFriendFunction(accountToken.getAccount().getId(),Integer.parseInt(friendId));
        List<Function> list = Lists.newArrayList();
        list.addAll(groupFunctions);
        list.addAll(friendFunctions);
        return list;
    }
}
