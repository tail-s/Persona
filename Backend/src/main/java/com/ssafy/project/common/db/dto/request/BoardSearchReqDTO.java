package com.ssafy.project.common.db.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class BoardSearchReqDTO {
    @Schema(description = "페이지번호", example = "0")
    private int page;
    @Schema(description = "검색 기준 컬럼명", example = "title")
    private String column;
    @Schema(description = "검색 단어", example = "연기")
    private String word;
    @Schema(description = "정렬기준", example = "id")
    private String sort;
}
