package com.ssafy.project.api.service;

import org.springframework.data.domain.Page;

public interface BoardLikeService {

    public void addBoardLike(Long boardId);
    public void removeBoardLike(Long boardId);
    public boolean checkBoardLike(Long boardId);
}
