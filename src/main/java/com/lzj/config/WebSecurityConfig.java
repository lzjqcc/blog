package com.lzj.config;

import com.lzj.VO.ResponseVO;
import com.lzj.security.*;
import com.lzj.utils.ComentUtils;
import com.lzj.utils.JsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * EnableWebSecurity 已经包含Configuration
 */
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.cors().disable();
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/friend/","/friend/*","/friend/**").authenticated();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authenticationProvider(new CustomProvider());
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/picture/*","/picture/**").
                // 使用SpringEl解析
                access("hasAuthority('group_picture_group_see') or hasAuthority('friend_picture_group_see')")
                .accessDecisionManager(new CustomAccessDecisionManager());
        // 如果需要在SpringSecurity相应类中添加自定义组件就i需要这个
        /*.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>(){
            @Override
            public FilterSecurityInterceptor postProcess(FilterSecurityInterceptor object) {
                object.setSecurityMetadataSource(new CustomSecurityMetadataSource());
                return object;
            }
        });*/


    }

    /**
     * 添加自定义的AuthenticationProcessingFilter
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
                }finally {
                    if (writer != null) {
                        ComentUtils.closeStream(writer);
                    }
                }
            }
        });
        return filter;
    }
    @Bean
    public CustomAccessDecisionManager customAccessDecisionManager() {
        CustomAccessDecisionManager customAccessDecisionManager = new CustomAccessDecisionManager();
        return customAccessDecisionManager;
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


}
