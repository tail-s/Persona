package com.ssafy.project.common.db.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class BoardAddReqDTO {
    @Schema(description = "비디오 아이디", example = "1")
    private Long videoId;
    @Schema(description = "제목", example = "게시글 제목입니다.")
    private String title;
    @Schema(description = "내용", example = "게시글 내용입니다.")
    private String content;
}
