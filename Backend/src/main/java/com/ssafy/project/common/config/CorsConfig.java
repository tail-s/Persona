package com.ssafy.project.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Value("${cors.allowedOrigins}")
    String[] allowedOrigins;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // cors로 인해 몇몇 기본헤더를 제외하고는 접근에 제한이있다. JWT인증을 위해 해당 헤더를 추가시킨다.
        config.addExposedHeader("Authorization");
        config.setAllowCredentials(true);
        for(String allowedOrigin: allowedOrigins) {
            config.addAllowedOriginPattern(allowedOrigin);
        }
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}

