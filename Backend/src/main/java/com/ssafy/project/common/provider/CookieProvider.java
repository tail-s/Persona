package com.ssafy.project.common.provider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public interface CookieProvider {

    Optional<Cookie> getCookie(HttpServletRequest request, String name);
    void addCookie(HttpServletResponse response, String name, String value, int maxAge);
    void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name);
    String serialize(Object object);
    <T> T deserialize(Cookie cookie, Class<T> cls);
}
