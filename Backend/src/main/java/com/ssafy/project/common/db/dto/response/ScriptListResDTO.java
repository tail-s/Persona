package com.ssafy.project.common.db.dto.response;

import com.ssafy.project.common.db.entity.base.EmotionEnum;
import com.ssafy.project.common.db.entity.base.GenreEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScriptListResDTO {

    private Long id;
    private String title;
    private String author;
    private String actor;
    private Long viewCnt;
    private EmotionEnum emotion;
    private GenreEnum genre;
    private LocalDateTime createdDate;
    private int bookmarkCnt;
    private int participantCnt;

//     @Builder
//     public ScriptListResDTO(Long id, String title, String author, String actor, String content, Long viewCnt, EmotionEnum emotion, GenreEnum genre, LocalDateTime createdDate, int bookmarkCnt, int participantCnt) {
//        this.id = id;
//        this.title = title;
//        this.author = author;
//        this.actor = actor;
//        this.viewCnt = viewCnt;
//        this.emotion = emotion;
//        this.genre = genre;
//        this.createdDate = createdDate;
//        this.bookmarkCnt = bookmarkCnt;
//        this.participantCnt = participantCnt;
//    }
}
