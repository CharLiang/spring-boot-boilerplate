/**
 * @(#)WebConfig.java, 2018-04-08.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.config;

import com.youdao.sortinghat.filter.XssFilter;
import com.youdao.sortinghat.interceptor.LogInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

/**
 * WebConfig
 *
 * @author 
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor());
    }

    @Bean
    public FilterRegistrationBean httpPutFormContentFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        HttpPutFormContentFilter httpPutFormContentFilter = new HttpPutFormContentFilter();
        registration.setFilter(httpPutFormContentFilter);
        registration.addUrlPatterns("/*");
        return registration;
    }

//    @Bean
//    public FilterRegistrationBean authFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        AuthFilter authFilter = new AuthFilter();
//        registration.setFilter(authFilter);
//        registration.addUrlPatterns("/*");
//        registration.setName("authFilter");
//        return registration;
//    }

    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        XssFilter xssFilter = new XssFilter();
        registration.setFilter(xssFilter);
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(1);
        registration.setAsyncSupported(true);
        return registration;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("10240KB");
        factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }

//    @Bean
//    public FilterRegistrationBean authFilterRegistration() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        AuthSingleProductFilter authSingleProductFilter = new AuthSingleProductFilter();
//        registrationBean.setFilter(authSingleProductFilter);
//        registrationBean.setName("auth_filter");
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.addInitParameter("product", "DICT");
//        registrationBean.addInitParameter("want_user_info", "false");
//        registrationBean.addInitParameter("ursMemoryCheck", "true");
//        registrationBean.addInitParameter("checkPersToken", "false");
//        registrationBean.addInitParameter("urs_login", "true");
//        registrationBean.addInitParameter("backend_relogin_domain", ".youdao.com");
//        return registrationBean;
//    }

}