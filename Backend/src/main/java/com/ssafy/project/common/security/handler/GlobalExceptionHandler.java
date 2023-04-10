package com.ssafy.project.common.security.handler;

import com.ssafy.project.common.security.exception.CommonApiException;
import com.ssafy.project.common.security.exception.CustomAuthException;
import com.ssafy.project.common.security.exception.CustomOAuth2Exception;
import com.ssafy.project.common.util.constant.ErrorCode;
import com.ssafy.project.common.util.dto.ErrorResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseStatusExceptionHandler {

    // @NotNull, @NotBlank 등의 발리데이션 익셉션 발생 시 핸들링하는 메소드 => validation 라이브러리 포함 안해서 미사용
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public void handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        String msg = e.getBindingResult()
//                .getAllErrors()
//                .get(0)
//                .getDefaultMessage();
//
//        throw new BadRequestException(msg);
//    }

    @ExceptionHandler(CommonApiException.class)
    public ResponseEntity<Object> handlerCommonApiException(CommonApiException e){
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(CustomAuthException.class)
    public ResponseEntity<Object> handleCustomAuthException(CustomAuthException e) {
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(CustomOAuth2Exception.class)
    public ResponseEntity<Object> handleCustomOAuth2Exception(CustomOAuth2Exception e) {
        return handleExceptionInternal(e.getErrorCode());
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ErrorResponseDTO makeErrorResponse(ErrorCode e) {
        return ErrorResponseDTO.of(e.name(), e.getMessage());
    }
}


