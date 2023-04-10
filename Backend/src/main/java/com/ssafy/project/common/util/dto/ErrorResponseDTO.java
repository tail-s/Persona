package com.ssafy.project.common.util.dto;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class ErrorResponseDTO {

    private final String code;
    private final String message;

    public static ErrorResponseDTO of(String code, String message){
        return new ErrorResponseDTO(code, message);
    }
}
