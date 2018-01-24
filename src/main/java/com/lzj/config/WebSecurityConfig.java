package com.lzj.config;

import com.google.common.collect.Lists;
import com.lzj.VO.ResponseVO;
import com.lzj.dao.FunctionDao;
import com.lzj.security.*;
import com.lzj.utils.ComentUtils;
import com.lzj.utils.JsonUtils;
import net.sf.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * EnableWebSecurity 已经包含Configuration
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    FunctionDao functionDao;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                ResponseVO responseVO = ComentUtils.buildResponseVO(false, "没有权限访问", null);
                PrintWriter writer = null;
                try {
                    response.setContentType("application/json");
                    writer = response.getWriter();
                    writer.write(JsonUtils.toJson(responseVO));
                    writer.flush();
                } finally {
                    ComentUtils.closeStream(writer);
                }


            }
        });
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                int i = 0;
                if (authException instanceof InsufficientAuthenticationException) {
                    ResponseVO responseVO = ComentUtils.buildResponseVO(false, authException.getMessage(), null);
                    PrintWriter writer = null;
                    try {
                        response.setContentType("application/json");
                        writer = response.getWriter();
                        writer.write(JsonUtils.toJson(responseVO));
                        writer.flush();
                    } finally {
                        ComentUtils.closeStream(writer);
                    }
                }
            }
        });
        http.cors().disable();
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/friend/", "/friend/*", "/friend/**").authenticated();
        http.authorizeRequests().antMatchers("/articles/insertArticles",
                "/articles/uploadArticlePic",
                "/articles/abandonArticle",
                "/articles/deleteArticle",
                "/comment/insertComment",
                "/uploadPicture",
                "/user/getUserDetail",
                "/user/updateUser",
                "/user/uploadHeadPortrait",
                "/group", "/group/*").authenticated();
        http.authorizeRequests().antMatchers("/loginBlog").permitAll();
        http.authenticationProvider(new CustomProvider());
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/pictureGroup/*", "/pictureGroup/**").
                // 内部使用SpringEl解析
                        access("hasAuthority('group_picture_group_see') and hasAuthority('friend_picture_group_see')").accessDecisionManager(imageAccessDecisionManager());

        // 如果需要在SpringSecurity相应类中添加自定义组件就i需要这个
        /*.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>(){
            @Override
            public FilterSecurityInterceptor postProcess(FilterSecurityInterceptor object) {
                object.setSecurityMetadataSource(new CustomSecurityMetadataSource());
                return object;
            }
        });*/
        // 会议控制权限
       /* http.authorizeRequests().antMatchers(HttpMethod.POST, "/conference/update")
                .access("hasAuthority('conference_group_see') and hasAuthority('conference_group_update')")
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public FilterSecurityInterceptor postProcess(FilterSecurityInterceptor object) {
                        object.setAccessDecisionManager(new ConferenceAccessDecisionManager(conferenceDao));
                        return object;
                    }
                });*/
    }

    public CustomAccessDecisionManager imageAccessDecisionManager() {
        CustomAccessDecisionManager manager = new CustomAccessDecisionManager();
        manager.setVoters(Lists.newArrayList(new ImageVoter(functionDao)));
        return manager;
    }

    /**
     * 添加自定义的AuthenticationProcessingFilter
     *
     * @return
     * @throws Exception
     */
    @Bean
    public CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter() throws Exception {
        AuthenticationManager authenticationManager = this.authenticationManager();
        CustomAuthenticationProcessingFilter filter = new CustomAuthenticationProcessingFilter(authenticationManager);
        filter.setAuthenticationSuccessHandler(new SuccessHandler());
        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override

            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                PrintWriter writer = null;
                try {
                    ResponseVO responseVO = new ResponseVO();
                    responseVO.setMessage("登录失败");
                    responseVO.setSuccess(false);
                    response.setContentType("application/json");
                    writer = response.getWriter();
                    writer.write(JsonUtils.toJson(responseVO));
                    writer.flush();
                } finally {
                    ComentUtils.closeStream(writer);
                }
            }
        });
        return filter;
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return customUserDetailServce();
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return customUserDetailServce();
    }

    @Bean
    public UserDetailsService customUserDetailServce() {
        return new CustomUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        CustomProvider provider = new CustomProvider();
        provider.setUserDetailsService(customUserDetailServce());
        auth.authenticationProvider(provider);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}
