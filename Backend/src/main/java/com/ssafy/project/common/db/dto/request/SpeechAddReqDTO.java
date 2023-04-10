package com.ssafy.project.common.db.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class SpeechAddReqDTO {
    private Long participantId;
    private String speechRecognition;
}
