package com.ssafy.project.api.controller;

import com.ssafy.project.api.service.VideoService;
import com.ssafy.project.common.db.dto.request.VideoAddReqDTO;
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

@Log4j2
@RestController
@Api(tags = {"비디오 API"})
@RequiredArgsConstructor
@RequestMapping(value = "/video")
public class VideoController {

    private final VideoService videoService;

    @Secured({"ROLE_CLIENT"})
    @PostMapping(value = "/save")
    @ApiOperation(value = "비디오 저장")
    public ResponseEntity<ResponseDTO> videoSave(@RequestBody VideoAddReqDTO videoAddReqDTO) {
        videoService.saveVideo(videoAddReqDTO);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_CREATE));
    }

    @Secured({"ROLE_CLIENT"})
    @DeleteMapping
    @ApiOperation(value = "비디오 삭제")
    public ResponseEntity<ResponseDTO> videoDelete(@RequestParam Long videoId){
        videoService.deleteVideo(videoId);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_DELETE));
    }

    @Secured({"ROLE_CLIENT"})
    @GetMapping
    @ApiOperation(value = "내 비디오 조회")
    public ResponseEntity<ResponseDTO> videoList(@RequestParam int page) {
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_READ, videoService.findAllVideo(page)));
    }

    @Secured({"ROLE_CLIENT"})
    @GetMapping("/{videoId}")
    @ApiOperation(value = "내 비디오 상세조회")
    public ResponseEntity<ResponseDTO> videoDetail(@PathVariable Long videoId) {
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_READ, videoService.detailVideo(videoId)));
    }
}
