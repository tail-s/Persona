package com.ssafy.project.common.db.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class VideoDetailResDTO {

    private String id;
    private String title;
    private String emotion;
    private String genre;
    private String actor;
    private String author;
    private String videoUrl;
    private String graphUrl;
    private String analysis;
    private String createdDate;
}
