package com.ssafy.project.common.db.dto.response;

import com.ssafy.project.common.db.entity.base.EmotionEnum;
import com.ssafy.project.common.db.entity.base.GenreEnum;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScriptDetailResDTO {
    private Long id;
    private String title;
    private String author;
    private String actor;
    private String content;
    private Long viewCnt;
    private EmotionEnum emotion;
    private GenreEnum genre;
    private LocalDateTime createdDate;
    private int bookmarkCnt;
    private int participantCnt;


}
