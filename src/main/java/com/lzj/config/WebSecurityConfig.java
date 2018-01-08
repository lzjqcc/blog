package com.lzj.config;

import com.lzj.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

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
        http.addFilterBefore(new CustomSecurityInterceptor(), FilterSecurityInterceptor.class);
        http.authenticationProvider(new CustomProvider());
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
    public CustomAuthenticationManager customAuthenticationManager() {
        return new CustomAuthenticationManager();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        CustomProvider provider = new CustomProvider();
        provider.setUserDetailsService(customUserDetailServce());
        auth.authenticationProvider(provider);
    }


}
