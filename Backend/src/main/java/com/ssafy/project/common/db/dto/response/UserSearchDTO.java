package com.ssafy.project.common.db.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UserSearchDTO {

    private String email;
    private String nickname;
    private String imageUrl;
}
