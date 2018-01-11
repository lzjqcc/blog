package com.lzj.security;

import com.lzj.VO.ResponseVO;
import com.lzj.domain.Account;
import com.lzj.utils.ComentUtils;
import com.lzj.utils.JsonUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PrintWriter writer = null;
        try {


            if (authentication != null) {
                ResponseVO<Account> responseVO = new ResponseVO<>();
                if (authentication instanceof AccountToken) {
                    AccountToken token = (AccountToken) authentication;
                    responseVO.setResult(token.getAccount());
                    responseVO.setSuccess(true);
                    responseVO.setMessage("登录成功");
                    response.setContentType("application/json");
                    writer = response.getWriter();
                    writer.write(JsonUtils.toJson(responseVO));
                    writer.flush();

                }
            }
        } finally {
            if (writer != null) {
                ComentUtils.closeStream(writer);
            }
        }
    }
}
