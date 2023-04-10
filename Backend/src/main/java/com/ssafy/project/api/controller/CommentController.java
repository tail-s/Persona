package com.ssafy.project.api.controller;

import com.ssafy.project.api.service.CommentService;
import com.ssafy.project.common.db.dto.request.CommentAddReqDTO;
import com.ssafy.project.common.db.dto.request.CommentModReqDTO;
import com.ssafy.project.common.db.dto.response.CommentDTO;
import com.ssafy.project.common.util.constant.Msg;
import com.ssafy.project.common.util.dto.ResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@Api(tags = {"댓글 API"})
@RequiredArgsConstructor
@RequestMapping(value = "/comment")
public class CommentController {

    private final CommentService commentService;

    @Secured({"ROLE_CLIENT"})
    @GetMapping("/all")
    @ApiOperation(value="전체 댓글 조회")
    public ResponseEntity<ResponseDTO> commentList(@RequestParam Long commentId, @RequestParam int page) {
        Page<CommentDTO> comments = commentService.findComment(commentId, page);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_READ, comments));
    }

    @Secured({"ROLE_CLIENT"})
    @GetMapping("/my")
    @ApiOperation(value="내 댓글 조회")
    public ResponseEntity<ResponseDTO> commentMyList(@RequestParam int page) {
        Page<CommentDTO> comments = commentService.findMyComment(page);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_READ, comments));
    }

    @Secured({"ROLE_CLIENT"})
    @PostMapping
    @ApiOperation(value="댓글 등록")
    public ResponseEntity<ResponseDTO> commentAdd(@RequestBody CommentAddReqDTO commentAddReqDTO){
        commentService.addComment(commentAddReqDTO);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_CREATE));
    }

    @Secured({"ROLE_CLIENT"})
    @PutMapping
    @ApiOperation(value="댓글 수정")
    public ResponseEntity<ResponseDTO> commentModify(@RequestBody CommentModReqDTO commentModReqDTO){
        commentService.modifyComment(commentModReqDTO);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_UPDATE));
    }

    @Secured({"ROLE_CLIENT"})
    @DeleteMapping
    @ApiOperation(value="댓글 삭제")
    public ResponseEntity<ResponseDTO> commentRemove(@RequestParam Long commentId){
        commentService.removeComment(commentId);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_DELETE));
    }
}
