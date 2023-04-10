package com.ssafy.project.api.controller;

import com.ssafy.project.api.service.ScriptService;
import com.ssafy.project.common.db.dto.request.ScriptSearchReqDTO;
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
@Api(tags = {"대본 API"})
@RequiredArgsConstructor
@RequestMapping(value = "/script")
public class ScriptController {

    private final ScriptService scriptService;

    @Secured({"ROLE_CLIENT"})
    //대본 조회
    @PostMapping("/all")
    @ApiOperation(value = "전체 대본 조회")
    public ResponseEntity<ResponseDTO> scriptList(@RequestBody ScriptSearchReqDTO scriptSearchReqDTO){
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_READ, scriptService.findAllScript(scriptSearchReqDTO)));
    }

    @Secured({"ROLE_CLIENT"})
    @GetMapping
    @ApiOperation(value = "대본 상세보기")
    public ResponseEntity<ResponseDTO> scriptDetail(@RequestParam Long scriptId){
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_READ, scriptService.detailScript(scriptId)));
    }
}
