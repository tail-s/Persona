package com.ssafy.project.api.controller;

import com.ssafy.project.api.service.BoardLikeService;
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
@RequiredArgsConstructor
@Api(tags = {"게시판 좋아요 API"})
@RequestMapping("/boardlike")
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    @Secured({"ROLE_CLIENT"})
    @PostMapping
    @ApiOperation(value = "게시글 좋아요")
    public ResponseEntity<ResponseDTO> boardLikeAdd(@RequestParam Long boardId){
        boardLikeService.addBoardLike(boardId);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_CREATE));
    }

    @Secured({"ROLE_CLIENT"})
    @DeleteMapping
    @ApiOperation(value = "게시글 좋아요 취소")
    public ResponseEntity<ResponseDTO> boardLikeRemove(@RequestParam Long boardId){
        boardLikeService.removeBoardLike(boardId);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_DELETE));
    }

    @Secured({"ROLE_CLIENT"})
    @GetMapping("/check")
    @ApiOperation(value = "좋아요 여부 확인")
    public ResponseEntity<ResponseDTO> boardCheck(@RequestParam Long scriptId){
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK,Msg.SUCCESS_READ, boardLikeService.checkBoardLike(scriptId)));
    }
}
