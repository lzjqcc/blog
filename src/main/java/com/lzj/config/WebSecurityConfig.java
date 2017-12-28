package com.lzj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * EnableWebSecurity 已经包含Configuration
 */
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.cors().disable();
        http.csrf().disable();
    }
}
