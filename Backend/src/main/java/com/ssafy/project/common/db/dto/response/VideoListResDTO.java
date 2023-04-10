package com.ssafy.project.common.db.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class VideoListResDTO {

    String id;
    String title;
    String thumbnailUrl;
    String analysis;
    String createdDate;

}
