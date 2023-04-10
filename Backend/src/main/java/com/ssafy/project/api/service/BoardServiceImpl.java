package com.ssafy.project.api.service;

import com.ssafy.project.common.db.dto.request.BoardAddReqDTO;
import com.ssafy.project.common.db.dto.request.BoardModifyReqDTO;
import com.ssafy.project.common.db.dto.response.BoardAllResDTO;
import com.ssafy.project.common.db.dto.response.BoardDetailResDTO;
import com.ssafy.project.common.db.entity.common.Board;
import com.ssafy.project.common.db.entity.common.User;
import com.ssafy.project.common.db.entity.common.Video;
import com.ssafy.project.common.db.repository.BoardRepository;
import com.ssafy.project.common.db.repository.UserRepository;
import com.ssafy.project.common.db.repository.VideoRepository;
import com.ssafy.project.common.provider.AuthProvider;
import com.ssafy.project.common.security.exception.CommonApiException;
import com.ssafy.project.common.util.constant.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final AuthProvider authProvider;

    @Override
    @Transactional(readOnly = true)
    public Page<BoardAllResDTO> findAllBoard(int page, String sort, String keyword) {
        Page<BoardAllResDTO> boards = boardRepository.findAllWithFilter(page, sort, keyword);
         return boards;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardAllResDTO> findTopBoard() {
        return boardRepository.findTop3Board();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoardAllResDTO> findMyBoard(int page) {
        Long userId = authProvider.getUserIdFromPrincipal();
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<Board> boards = boardRepository.findByUserId(userId, pageable);
        Page<BoardAllResDTO> boardAllResDTOS = boards.map(board ->
                BoardAllResDTO.builder()
                .id(board.getId())
                .nickName(board.getUser().getNickname())
                .createdDate(board.getCreatedDate())
                .title(board.getTitle())
                .content(board.getContent())
                .viewCnt(board.getViewCnt())
                .likeCnt(board.getBoardLikes().size())
                .commentCnt(board.getComments().size())
                .build());
        return boardAllResDTOS;
    }

    @Override
    public BoardDetailResDTO detailBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CommonApiException(CommonErrorCode.BOARD_NOT_FOUND));

        board.setViewCnt(board.getViewCnt()+1L);

        BoardDetailResDTO boardDetailResDTO = BoardDetailResDTO.builder()
                .id(board.getId())
                .nickName(board.getUser().getNickname())
                .createdDate(board.getCreatedDate())
                .viewCnt(board.getViewCnt())
                .title(board.getTitle())
                .content(board.getContent())
                .likeCnt(board.getBoardLikes().size())
                .commentCnt(board.getComments().size())
                .videoUrl(board.getVideo().getVideoUrl())
                .build();
        return boardDetailResDTO;
    }
    @Override
    public void addBoard(BoardAddReqDTO boardAddReqDTO) {
        Video video = null;
        if(boardAddReqDTO.getVideoId() != null)
        video = videoRepository.findById(boardAddReqDTO.getVideoId()).orElseThrow(() -> new CommonApiException(CommonErrorCode.VIDEO_NOT_FOUND));
        User user = userRepository.findById(authProvider.getUserIdFromPrincipal()).orElseThrow(() -> new CommonApiException(CommonErrorCode.USER_NOT_FOUND));

        Board board = Board.builder()
                .video(video)
                .title(boardAddReqDTO.getTitle())
                .content(boardAddReqDTO.getContent())
                .user(user)
                .build();

        user.getBoards().add(board);
    }

    @Override
    public void modifyBoard(BoardModifyReqDTO boardModifyReqDTO) {
        Board board = boardRepository.findById(boardModifyReqDTO.getBoardId()).orElseThrow(() -> new CommonApiException(CommonErrorCode.BOARD_NOT_FOUND));
        if(authProvider.getUserIdFromPrincipal() != board.getUser().getId()) throw new CommonApiException(CommonErrorCode.BOARD_NOT_ALLOWED);
        board.setContent(boardModifyReqDTO.getContent());
        board.setTitle(boardModifyReqDTO.getTitle());
    }

    @Override
    public void removeBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new CommonApiException(CommonErrorCode.BOARD_NOT_FOUND));
        if(authProvider.getUserIdFromPrincipal() != board.getUser().getId()) throw new CommonApiException(CommonErrorCode.BOARD_NOT_ALLOWED);
        boardRepository.deleteById(id);
    }
}
