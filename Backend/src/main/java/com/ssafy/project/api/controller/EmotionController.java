package com.ssafy.project.api.controller;

import com.ssafy.project.api.service.EmotionService;
import com.ssafy.project.common.db.dto.request.EmotionAddReqDTO;
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
@Api(tags = {"감정 API"})
@RequiredArgsConstructor
@RequestMapping(value = "/emotion")
public class EmotionController {

    private final EmotionService emotionService;

    @Secured({"ROLE_CLIENT"})
    @PostMapping("/emotion")
    @ApiOperation(value = "감정 정보 등록")
    public ResponseEntity<ResponseDTO> emotionAdd(@RequestBody EmotionAddReqDTO emotionAddReqDTO){
        emotionService.addEmotion(emotionAddReqDTO);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_CREATE));
    }
}
