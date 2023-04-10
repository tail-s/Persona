package com.ssafy.project.common.db.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class EmotionAddReqDTO {

    private Long participantId;
    private double time;
    private double pleasure;
    private double embarrassed;
    private double anger;
    private double anxiety;
    private double hurt;
    private double sad;
    private double neutrality;
}
