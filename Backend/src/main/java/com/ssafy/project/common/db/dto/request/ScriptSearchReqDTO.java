package com.ssafy.project.common.db.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class ScriptSearchReqDTO {

    private String option;
    private String keyword;
    private List<String> emotions = new ArrayList<>();
    private List<String> genres = new ArrayList<>();
    private int page;
    private String sort;
}
