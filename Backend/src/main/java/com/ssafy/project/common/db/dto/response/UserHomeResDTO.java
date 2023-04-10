package com.ssafy.project.common.db.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UserHomeResDTO {

    private String nickname;
    private String email;
    private String imageUrl;
}
