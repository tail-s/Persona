package com.ssafy.project.common.db.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class BoardModifyReqDTO {
    @Schema(description = "게시글 id", example = "1")
    private Long boardId;
    @Schema(description = "게시글 제목", example = "게시글 제목입니다.")
    private String title;
    @Schema(description = "게시글 내용", example = "게시글 내용입니다.")
    private String content;
}
