package com.ssafy.project.api.controller;

import com.ssafy.project.api.service.BookmarkService;
import com.ssafy.project.common.util.constant.Msg;
import com.ssafy.project.common.util.dto.ResponseDTO;
import com.ssafy.project.common.provider.AuthProvider;
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
@Api(tags = {"대본 북마크 API"})
@RequiredArgsConstructor
@RequestMapping(value = "/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final AuthProvider authProvider;

    @Secured({"ROLE_CLIENT"})
    @PostMapping
    @ApiOperation(value="북마크 생성")
    public ResponseEntity<ResponseDTO> bookmarkAdd(@RequestParam Long scriptId) {
        bookmarkService.addBookmark(scriptId);
       return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_CREATE));
    }

    @Secured({"ROLE_CLIENT"})
    @DeleteMapping
    @ApiOperation(value="북마크 삭제")
    public ResponseEntity<ResponseDTO> bookmarkRemove(@RequestParam Long scriptId){
        Long userId = authProvider.getUserIdFromPrincipal();
//        Long userId = 1L;
        bookmarkService.removeBookmark(scriptId);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_DELETE));
    }

    @Secured({"ROLE_CLIENT"})
    @GetMapping("/check")
    @ApiOperation(value = "북마크 여부 확인")
    public ResponseEntity<ResponseDTO> bookmarkCheck(@RequestParam Long scriptId){
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_READ, bookmarkService.checkBookmark(scriptId)));
    }

    @Secured({"ROLE_CLIENT"})
    @GetMapping("/my")
    @ApiOperation(value = "북마크 목록 조회")
    public ResponseEntity<ResponseDTO> bookmarkMyList(@RequestParam int page){
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_READ, bookmarkService.findMyBookmark(page)));
    }
}
