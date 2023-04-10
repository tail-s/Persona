package com.ssafy.project.common.util.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class ResponseDTO {

    private HttpStatus status;
    private String message;
    private Object value;

    public ResponseDTO(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ResponseDTO of(HttpStatus status, String message, Object value) {
        return new ResponseDTO(status, message, value);
    }
    public static ResponseDTO of(HttpStatus status, String message) {
        return new ResponseDTO(status, message);
    }
}
