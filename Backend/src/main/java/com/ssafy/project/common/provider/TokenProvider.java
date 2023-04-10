package com.ssafy.project.common.provider;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public interface TokenProvider {

    String createToken(Authentication authentication);
    Long getUserIdFromToken(String token);
    boolean validateToken(String authToken);
    String getTokenFromRequest(HttpServletRequest request);
    Date getExpireTime(String token);
}
