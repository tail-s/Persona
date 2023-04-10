package com.ssafy.project.common.provider;

import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Optional;


@Service
public class CookieProviderImpl implements CookieProvider{
    @Override
    public Optional<Cookie> getCookie(HttpServletRequest request, String name) {

     Cookie[] cookies = request.getCookies();

     if(cookies != null) {

         for(Cookie cookie : cookies) {
             if(cookie.getName().equals(name)) {
                 return Optional.of(cookie);
             }
         }
     }

        return Optional.empty();
    }

    @Override
    public void addCookie(HttpServletResponse response, String name, String value, int maxAge) {

        Cookie cookie = new Cookie(name, value);

        cookie.setPath("/");

        // 보안 관련 설정
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);

        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    @Override
    public void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);

                    response.addCookie(cookie);
                }
            }
        }
    }

    @Override
    public String serialize(Object object) {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(object));
    }

    @Override
    public <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.getValue())));
    }
}
