package com.ssafy.project.common.provider;

import com.ssafy.project.common.security.authentication.UserPrincipal;
import com.ssafy.project.common.security.exception.CommonApiException;
import com.ssafy.project.common.util.constant.CommonErrorCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Log4j2
@Service
public class AuthProviderImpl implements AuthProvider {

    @Override
    public UserPrincipal getUserPricipalFromAuthentication(Authentication authentication) {
        return (UserPrincipal) authentication.getPrincipal();
    }

    @Override
    public long getUserIdFromPrincipal() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(String.valueOf(principal).equals("anonymousUser")) {
            throw new CommonApiException(CommonErrorCode.UNLOGINED_USER);
        }

        // UserPrincipal로의 형변환도 가능하나, Principal에 Userdetails를 set하므로, 굳이 필요가 없는 듯 함
        // Principal은 Authentication내에 Principal이 아닌 Object객체로 저장되어있으므로, getUsername()과 같은 메소드가 없다.
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return Long.parseLong(userDetails.getUsername());
    }
}
