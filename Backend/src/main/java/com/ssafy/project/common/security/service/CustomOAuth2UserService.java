package com.ssafy.project.common.security.service;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.ssafy.project.common.db.dto.social.*;
import com.ssafy.project.common.db.entity.base.SocialEnum;
import com.ssafy.project.common.db.entity.common.User;
import com.ssafy.project.common.db.repository.UserRepository;
import com.ssafy.project.common.security.authentication.UserPrincipal;
import com.ssafy.project.common.util.constant.CommonErrorCode;
import com.ssafy.project.common.security.exception.CustomOAuth2Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

//        Map<String, Object> mapForLog = oAuth2User.getAttributes();
//        mapForLog.forEach((k, v) -> log.info("{} : {}", k,v));

        OAuth2UserInfo oAuth2UserInfo = getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );

        if (StringUtils.isBlank(oAuth2UserInfo.getEmail()) || oAuth2UserInfo.getEmail().equals("null")) {
            log.info("이메일");
            throw new CustomOAuth2Exception(CommonErrorCode.NO_EMAIL_PROVIDED);
        }

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());

        User user;

        if (userOptional.isPresent()) {
            // 참조 자체로도 에러발생 코드
//            log.info(!userOptional.get().getSocialAuth().getSocialType().equals(SocialEnum.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId())));
            user = updateUser(userOptional.get(), oAuth2UserInfo);
            }
         else {
            user = registerUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {

        if (registrationId.equalsIgnoreCase(SocialEnum.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(SocialEnum.naver.toString())) {
            return new NaverOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(SocialEnum.kakao.toString())) {
            return new KakaoOAuth2UserInfo(attributes);
        } else {
            log.info("다른소셜");
            throw new CustomOAuth2Exception(CommonErrorCode.BAD_SOCIAL_TYPE);
        }
    }

    private User registerUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {

        return userRepository.save(User.builder()
                .email(oAuth2UserInfo.getEmail())
                .nickname(oAuth2UserInfo.getName())
                .socialAuth(SocialAuth.builder()
                        .providerId(oAuth2UserInfo.getId())
                        .socialType(SocialEnum.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                        .email(oAuth2UserInfo.getEmail())
                        .name(oAuth2UserInfo.getName())
                        .imageUrl(oAuth2UserInfo.getImageUrl())
                        .build())
                .build());
    }

    private User updateUser(User user, OAuth2UserInfo oAuth2UserInfo) {

    // protected된 User 객체에 각 속성을 set하여 exception 발생하였었음
        user.getSocialAuth().update(oAuth2UserInfo.getName(), oAuth2UserInfo.getImageUrl());
        return userRepository.save(user);
    }

}