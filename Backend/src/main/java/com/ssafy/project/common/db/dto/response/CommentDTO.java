package com.ssafy.project.common.db.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentDTO {
    @Schema(description = "댓글 아이디")
    private Long id;
    @Schema(description = "유저 프로필 url")
    private String userProfile;
    @Schema(description = "닉네임")
    private String nickname;
    @Schema(description = "유저 이메일")
    private String email;
    @Schema(description = "내용")
    private String content;
    @Schema(description = "글 제목")
    private String title;
    @Schema(description = "생성일시")
    private LocalDateTime createdDate;
}
