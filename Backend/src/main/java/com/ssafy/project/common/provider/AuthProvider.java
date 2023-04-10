package com.ssafy.project.common.provider;

import com.ssafy.project.common.security.authentication.UserPrincipal;
import org.springframework.security.core.Authentication;

public interface AuthProvider {

    UserPrincipal getUserPricipalFromAuthentication(Authentication authentication);

    long getUserIdFromPrincipal();

}
