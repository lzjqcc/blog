package com.lzj.filter;

import com.lzj.exception.BusinessException;
import com.lzj.utils.ComentUtils;
import com.lzj.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ServletRequestHandledEvent;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 头像上传大于1M处理MultipartException
 */
public class UploadIconFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    //WebApplicationContext
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");

            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");

            response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,withCredentials");

            response.setHeader("Access-Control-Allow-Credentials", "true");

            HttpServletRequest request = (HttpServletRequest) servletRequest;

            System.out.println(servletRequest.getParameter("groupId")+"***************"+request.getQueryString());
            filterChain.doFilter(servletRequest, response);
        } catch (MultipartException e) {
            HttpServletResponse response= (HttpServletResponse) servletResponse;
            PrintWriter writer = servletResponse.getWriter();
            response.setCharacterEncoding("UTF-8");
            response.setLocale(Locale.CHINESE);
            response.setContentType("application/json");
            Map<String, String> map = new HashMap<>();
            map.put("error", "上传图片不能大于1M");
            writer.write(JsonUtils.toJson(map));
            writer.flush();
            writer.close();
        }

    }


    @Override
    public void destroy() {

    }
}
