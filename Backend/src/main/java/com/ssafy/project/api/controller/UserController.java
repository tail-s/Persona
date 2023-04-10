package com.ssafy.project.api.controller;

import com.ssafy.project.api.service.UserService;
import com.ssafy.project.common.util.constant.Msg;
import com.ssafy.project.common.util.dto.ResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@RestController
@Api(tags = {"유저 API"})
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    // 회원가입, 로그인 Api Oauth2에서 진행

    @Secured({"ROLE_CLIENT"})
    @GetMapping("/logout")
    @ApiOperation(value="로그아웃")
    public ResponseEntity<ResponseDTO> userLogout(HttpServletRequest request){
        userService.logoutUser(request);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_LOGOUT));
    }

    @Secured({"ROLE_CLIENT"})
    @GetMapping
    @ApiOperation(value="메인화면 유저 정보")
    public ResponseEntity<ResponseDTO> userHome(){
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_READ, userService.homeUser()));
    }

    // 유저 본인이 더 상세한 정보를 받을 때
    @Secured({"ROLE_CLIENT"})
    @GetMapping("/mypage")
    @ApiOperation(value="마이페이지 조회")
    public ResponseEntity<ResponseDTO> userDetailById(){
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_READ, userService.detailUserById()));
    }

    // 다른 사람의 정보를 조회 할 때
    @Secured({"ROLE_CLIENT"})
    @GetMapping("/{email}")
    @ApiOperation(value="유저 조회")
    public ResponseEntity<ResponseDTO> userDetailByEmail(@PathVariable String email){
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_READ, userService.detailUserByEmail(email)));
    }

    @Secured({"ROLE_CLIENT"})
    @DeleteMapping
    @ApiOperation(value="유저 탈퇴")
    public ResponseEntity<ResponseDTO> userDelete(){
        userService.deleteUser();
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_DELETE));
    }

//    @PutMapping
//    @ApiOperation(value="유저 수정")
//    public ResponseEntity<ResponseDTO> userModify(@RequestBody UserModifyReqDTO userModifyReqDto){
//        userService.modifyUser(userModifyReqDto);
//        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_USER_MODIFY));
//    }
}
