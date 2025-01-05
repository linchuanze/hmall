package com.hmall.api.config;

import com.hmall.common.utils.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfig {

    @Bean
    public RequestInterceptor userInfoRequestInterceptor() {
       return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                /*
                    因为请求是从网关转发的，所以请求头存下的用户信息，服务拦截后有用户信息存在threadlocal，
                    服务调用feign发起请求被fegin拦截器拦截，可以从threadlocal获得用户信息
                 */
                Long userId = UserContext.getUser();
                if (userId != null) {
                    requestTemplate.header("user-info", userId.toString());
                }
            }
        };
    }
}
