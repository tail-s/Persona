package com.ssafy.project.api.service;

import com.ssafy.project.common.db.dto.request.BoardAddReqDTO;
import com.ssafy.project.common.db.dto.request.BoardModifyReqDTO;
import com.ssafy.project.common.db.dto.request.BoardSearchReqDTO;
import com.ssafy.project.common.db.dto.response.BoardAllResDTO;
import com.ssafy.project.common.db.dto.response.BoardDetailResDTO;
import com.ssafy.project.common.db.entity.common.Board;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoardService {

    //조회
    Page<BoardAllResDTO> findAllBoard(int page, String sort, String keyword);
    List<BoardAllResDTO> findTopBoard();
    Page<BoardAllResDTO> findMyBoard(int page);
    BoardDetailResDTO detailBoard(Long boardId);
    //등록
    void addBoard(BoardAddReqDTO boardAddReqDTO);
    //수정
    void modifyBoard(BoardModifyReqDTO boardModifyReqDTO);
    //삭제
     void removeBoard(Long id);
}
