package com.lzj.config;

import com.lzj.filter.UploadIconFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

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
     * 注册过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean helloFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("hello");
        UploadIconFilter helloFilter = new UploadIconFilter();
        registrationBean.setFilter(helloFilter);
        beanFactory.autowireBean(helloFilter);
        return registrationBean;
    }
}
