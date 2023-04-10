package com.ssafy.project.common.db.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoAddReqDTO {

    String videoUrl;
    String thumbnailUrl;
    String graphUrl;
    String title;
    Long participantId;
    String analysis;
}
