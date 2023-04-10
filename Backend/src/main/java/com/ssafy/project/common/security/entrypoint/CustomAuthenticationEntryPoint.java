package com.ssafy.project.common.security.entrypoint;


import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
// AuthenticationEntryPoint => 인증에 실패하면 호출
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    // 인증 오류를 처리하는 메소드
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {

        response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getLocalizedMessage());
    }
}