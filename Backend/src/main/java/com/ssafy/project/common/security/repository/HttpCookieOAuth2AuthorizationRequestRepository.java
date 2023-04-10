package com.ssafy.project.common.security.repository;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.ssafy.project.common.provider.CookieProvider;
import com.ssafy.project.common.provider.CookieProviderImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@Component
public class HttpCookieOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    public final CookieProvider cookieProvider;

    public HttpCookieOAuth2AuthorizationRequestRepository() {
        this.cookieProvider = new CookieProviderImpl();
    }

    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    private static final int COOKIE_EXPIRE_SECONDS = 180;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {

        // 해당 이름으로 설정된 쿠키를 역직렬화하여, request 리턴
        return cookieProvider.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> cookieProvider.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {

        if (authorizationRequest == null) {
            removeAuthorizationRequest(request, response);
            return;
        }

        // request 직렬화하여 쿠키에 저장
        cookieProvider.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, cookieProvider.serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS);

        // 로그인 이후 리디렉션 될 uri
        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);

        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            cookieProvider.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, COOKIE_EXPIRE_SECONDS);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        cookieProvider.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        cookieProvider.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
    }
}