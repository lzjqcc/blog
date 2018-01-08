package com.lzj.security;

import com.lzj.domain.Account;
import com.lzj.exception.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 前端利用 Json 格式登陆
 */
public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    public CustomAuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/loginBlog", "POST"));
        setAuthenticationManager(authenticationManager);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equals("POST")) {
            throw new BusinessException(400, "请求姿势不对");
        }
        Account account = getAccountFromRequest(request);
        if (StringUtils.isEmpty(account.getEmail()) || StringUtils.isEmpty(account.getPassword())) {
            throw new BusinessException(401, "email or password不能为空");
        }
        account.setEmail(account.getEmail().trim());
        account.setPassword(account.getPassword().trim());
        AccountToken authRequest = new AccountToken(account.getEmail(), account.getPassword(), null);
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 从Json中获取Account对象
     * @param request
     * @return
     * @throws IOException
     */
    private Account getAccountFromRequest(HttpServletRequest request) throws IOException {
        HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        return (Account) messageConverter.read(Account.class, new MappingJacksonInputMessage(request.getInputStream(), null));

    }


    protected void setDetails(HttpServletRequest request,
                              AccountToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
