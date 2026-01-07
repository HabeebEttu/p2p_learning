package com.habeeb.p2plearn.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.multipart.support.MultipartFilter;

@Configuration
public class MultipartSecurityConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public MultipartFilter multipartFilter() {
        MultipartFilter filter = new MultipartFilter();
        filter.setMultipartResolverBeanName("multipartResolver");
        return filter;
    }

}
