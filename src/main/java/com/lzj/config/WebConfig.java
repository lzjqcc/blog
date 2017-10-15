package com.lzj.config;

import com.lzj.filter.ParseURLFilter;
import com.lzj.filter.UploadIconFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 与实体有关的配置
 */
@Configuration
public class WebConfig {
    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    /**
     * 文件上传
     * @return
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getMutipart(){
        CommonsMultipartResolver resolver=new CommonsMultipartResolver();
        resolver.setMaxUploadSize(1024*1024);
        resolver.setResolveLazily(true);
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }

    /**
     * 加上这个上传文件才有用
     * @return
     */
    @Bean
    public MultipartFilter multipartFilter() {
        MultipartFilter multipartFilter = new MultipartFilter();
        multipartFilter.setMultipartResolverBeanName("multipartReso‌‌lver");
        return multipartFilter;
    }

    /**
     * 注册过滤器UploadIconFilter
     * @return
     */
    @Bean
    @Order(2)
    public FilterRegistrationBean uploadIconFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("hello");
        UploadIconFilter helloFilter = new UploadIconFilter();

        registrationBean.setFilter(helloFilter);
        return registrationBean;
    }
    /*spring-webscoket 消息推送
    * */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
 /*   @Bean
    @Order(1)
    public FilterRegistrationBean parseURLFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("parseURL");
        ParseURLFilter filter=new ParseURLFilter();
        registrationBean.setFilter(filter);
        return registrationBean;
    }*/

}
