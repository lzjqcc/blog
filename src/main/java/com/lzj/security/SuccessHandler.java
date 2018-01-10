package com.lzj.security;

import com.lzj.VO.ResponseVO;
import com.lzj.domain.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {
            ResponseVO<Account> responseVO = new ResponseVO<>();
            if (authentication instanceof AccountToken) {
                AccountToken token = (AccountToken) authentication;
                responseVO.setResult(token.getAccount());
                responseVO.setSuccess(true);
                responseVO.setMessage("登录成功");
            }
        }
    }
}
