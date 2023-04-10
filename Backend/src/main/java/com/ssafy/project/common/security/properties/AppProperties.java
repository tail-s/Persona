package com.ssafy.project.common.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    @Getter
    @Setter
    public static class Auth {
        private String tokenSecret;

        private long tokenExpirationMsec;
    }

    @Getter
    public static class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();

        // @ConfigurationProperties에서, 카멜 케이스(camelCase)를 하이픈(-)으로 구분된 단어로 자동 매핑시킴
        // authorized-redirect-uris -> authorizedRedirectUris
        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;

            return this;
        }
    }

}