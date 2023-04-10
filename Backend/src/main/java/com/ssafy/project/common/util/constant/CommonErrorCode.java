package com.ssafy.project.common.util.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    //게시글
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."),
    BOARD_NOT_ALLOWED(HttpStatus.NOT_ACCEPTABLE, "다른 유저의 게시글입니다."),
    //댓글
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),
    COMMENT_NOT_ALLOWED(HttpStatus.NOT_ACCEPTABLE, "다른 유저의 댓글입니다."),
    //유저
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    UNLOGINED_USER(HttpStatus.FORBIDDEN, "로그인되지 않은 유저입니다."),
    USER_EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다."),

    //북마크
    BOOKMARK_ALREADY_EXIST(HttpStatus.ALREADY_REPORTED, "이미 존재하는 북마크 입니다."),
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 북마크입니다."),

    //비디오
    VIDEO_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 영상입니다."),
    VIDEO_NOT_ALLOWED(HttpStatus.NOT_ACCEPTABLE, "다른 유저의 동영상입니다."),

    //참여자
    PARTICIPANT_NOT_FOUND(HttpStatus.NOT_FOUND, "대본 연습에 참가한 이력이 없습니다."),

    //대본
    SCRIPT_NOT_FOUND(HttpStatus.NOT_FOUND,"대본이 존재하지 않습니다."),

    //파일
    FILE_NOT_VALID(HttpStatus.BAD_REQUEST,"유효하지 않은 파일 형식입니다."),

    //인증
    NO_SET_AUTHENTICATION(HttpStatus.FORBIDDEN, "권한 인증에 실패했습니다."),

    //소셜인증
    UNAUTHORIZED_URI(HttpStatus.UNAUTHORIZED, "등록되지 않은 Uri입니다."),
    NO_EMAIL_PROVIDED(HttpStatus.UNAUTHORIZED, "이메일을 제공받지 못했습니다."),
    EMAIL_ALREADY_EXITS(HttpStatus.ALREADY_REPORTED, "이미 존재하는 이메일 입니다."),
    BAD_SOCIAL_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 소셜 타입입니다.");
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
