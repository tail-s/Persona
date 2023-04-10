package com.ssafy.project.api.controller;

import com.ssafy.project.api.service.SpeechService;
import com.ssafy.project.common.db.dto.request.SpeechAddReqDTO;
import com.ssafy.project.common.util.constant.Msg;
import com.ssafy.project.common.util.dto.ResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@Api(tags = {"음성인식 API"})
@RequiredArgsConstructor
@RequestMapping(value = "/speech")
public class SpeechController {

    private final SpeechService speechService;

    @Secured({"ROLE_CLIENT"})
    @PostMapping("/speech")
    @ApiOperation(value = "음성 인식 정보 등록")
    public ResponseEntity<ResponseDTO> speechAdd(@RequestBody SpeechAddReqDTO speechAddReqDTO){
        speechService.addSpeech(speechAddReqDTO);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_CREATE));
    }
}
