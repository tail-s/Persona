package com.ssafy.project.api.service;

import com.ssafy.project.common.db.dto.request.UserModifyReqDTO;
import com.ssafy.project.common.db.dto.response.UserDetailResDTO;
import com.ssafy.project.common.db.dto.response.UserHomeResDTO;
import com.ssafy.project.common.db.dto.response.UserSearchDTO;
import com.ssafy.project.common.db.entity.common.User;
import com.ssafy.project.common.db.repository.UserRepository;
import com.ssafy.project.common.provider.AuthProvider;
import com.ssafy.project.common.provider.RedisProvider;
import com.ssafy.project.common.provider.TokenProvider;
import com.ssafy.project.common.security.exception.CommonApiException;
import com.ssafy.project.common.util.constant.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final AuthProvider authProvider;

    private final TokenProvider tokenProvider;

    private final RedisProvider redisProvider;

    @Transactional
    @Override
    public void logoutUser(HttpServletRequest request) {

        String token = tokenProvider.getTokenFromRequest(request);
        Long id = tokenProvider.getUserIdFromToken(token);

        Date expireTime = tokenProvider.getExpireTime(token);

        Date nowTime = new Date();
        long remainTime = expireTime.getTime() - nowTime.getTime();

        redisProvider.setBlackList(token, id, remainTime , TimeUnit.MILLISECONDS);
    }

    @Transactional
    @Override
    public UserHomeResDTO homeUser() {

        long id = authProvider.getUserIdFromPrincipal();
        User user = userRepository.findById(id).orElseThrow(() -> new CommonApiException(CommonErrorCode.UNLOGINED_USER));

        return UserHomeResDTO.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .imageUrl(user.getSocialAuth().getImageUrl()).build();
    }

    @Transactional
    @Override
    public UserDetailResDTO detailUserById() {

        long id = authProvider.getUserIdFromPrincipal();
        User user = userRepository.findById(id).orElseThrow(() -> new CommonApiException(CommonErrorCode.UNLOGINED_USER));

        return UserDetailResDTO.builder()
                .email(user.getEmail())
                .imageUrl(user.getSocialAuth().getImageUrl())
                .nickname(user.getNickname())
                .socialType(String.valueOf(user.getSocialAuth().getSocialType()))
                .createdDate(user.getCreatedDate().toString()).build();
    }

    @Transactional
    @Override
    public UserSearchDTO detailUserByEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new CommonApiException(CommonErrorCode.USER_EMAIL_NOT_FOUND));

        return UserSearchDTO.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .imageUrl(user.getSocialAuth().getImageUrl()).build();
    }

    @Transactional
    @Override
    public void deleteUser() {

        long id = authProvider.getUserIdFromPrincipal();
        User user = userRepository.findById(id).orElseThrow(() -> new CommonApiException(CommonErrorCode.USER_NOT_FOUND));
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void modifyUser(UserModifyReqDTO userModifyReqDto) {

        long id = authProvider.getUserIdFromPrincipal();
        User user = userRepository.findById(id).orElseThrow(() -> new CommonApiException(CommonErrorCode.USER_NOT_FOUND));
        user.setNickname(userModifyReqDto.getNickname());
        userRepository.save(user);
    }
}
