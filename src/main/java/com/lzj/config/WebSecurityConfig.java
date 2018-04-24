package com.lzj.config;

import com.google.common.collect.Lists;
import com.lzj.VO.ResponseVO;
import com.lzj.dao.FunctionDao;
import com.lzj.dao.dto.AccountDto;
import com.lzj.domain.Account;
import com.lzj.filter.UploadIconFilter;
import com.lzj.security.*;
import com.lzj.utils.ComentUtils;
import com.lzj.utils.JsonUtils;
import net.sf.json.JSON;
import org.springframework.beans.BeanUtils;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * EnableWebSecurity 已经包含Configuration
 */
@EnableWebSecurity
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    FunctionDao functionDao;
    @Autowired
    SessionRepository sessionRepository;
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
        http.addFilterBefore(new UploadIconFilter(), ChannelProcessingFilter.class);
        http.logout().logoutUrl("/registerOut").invalidateHttpSession(true).logoutSuccessHandler(new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.setContentType("application/json");
                OutputStream outputStream = response.getOutputStream();
                try {

                    if (authentication instanceof AccountToken) {
                        AccountToken token = (AccountToken) authentication;
                        ResponseVO<AccountDto> responseVO = new ResponseVO<>();
                        AccountDto dto = new AccountDto();
                        BeanUtils.copyProperties(token.getAccount(), dto);
                        dto.setPassword(null);
                        responseVO.setResult(dto);
                        responseVO.setSuccess(true);
                        responseVO.setMessage("操作成功");
                        outputStream.write(JsonUtils.toJson(responseVO).getBytes());
                    }
                }finally {
                    sessionRepository.delete(request.getRequestedSessionId());
                    ComentUtils.closeStream(outputStream);
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
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/friend/", "/friend/*", "/friend/**").authenticated();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/friend/", "/friend/*", "/friend/**").authenticated();
        // 由于Post 请求之前会产生一个option请求，额这个option请求过来的时候不会携带session信息，因此对option请求放过
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/group/", "/group/*", "/group/**").authenticated();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/group/", "/group/*", "/group/**").authenticated();

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/message/", "/message/*", "/message/**").authenticated();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/message/", "/message/*", "/message/**").authenticated();

        http.authorizeRequests().antMatchers(HttpMethod.GET,"/articles/insertArticles",
                "/articles/uploadArticlePic",
                "/articles/abandonArticle",
                "/articles/deleteArticle",
                /*"/comment/insertComment",*/
                "/uploadPicture",
                "/user/getUserDetail",
                "/user/updateUser",
                "/user/uploadHeadPortrait").authenticated();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/articles/insertArticles",
                "/articles/uploadArticlePic",
                "/articles/abandonArticle",
                "/articles/deleteArticle",
                /*"/comment/insertComment",*/
                "/uploadPicture",
                "/user/getUserDetail",
                "/user/updateUser",
                "/user/uploadHeadPortrait").authenticated();
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
                    ResponseVO<String> responseVO = new ResponseVO();
                    responseVO.setMessage("登录失败");
                    responseVO.setSuccess(false);
                    responseVO.setResult(exception.toString());
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
