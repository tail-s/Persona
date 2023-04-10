package com.ssafy.project.api.service;

import com.ssafy.project.common.db.entity.common.Board;
import com.ssafy.project.common.db.entity.common.BoardLike;
import com.ssafy.project.common.db.entity.common.User;
import com.ssafy.project.common.db.repository.BoardLikeRepository;
import com.ssafy.project.common.db.repository.BoardRepository;
import com.ssafy.project.common.db.repository.UserRepository;
import com.ssafy.project.common.provider.AuthProvider;
import com.ssafy.project.common.security.exception.CommonApiException;
import com.ssafy.project.common.util.constant.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class BoardLikeServiceImpl implements BoardLikeService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final AuthProvider authProvider;

    @Override
    public void addBoardLike(Long boardId) {
        Long userId = authProvider.getUserIdFromPrincipal();
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new CommonApiException(CommonErrorCode.BOARD_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(()-> new CommonApiException(CommonErrorCode.USER_NOT_FOUND));

        if(boardLikeRepository.findByUserIdAndBoardId(boardId, boardId).isPresent()) throw new CommonApiException(CommonErrorCode.BOOKMARK_ALREADY_EXIST);

        BoardLike boardLike = BoardLike.builder()
                .board(board)
                .user(user)
                .build();

        board.getBoardLikes().add(boardLike);
    }

    @Override
    public void removeBoardLike(Long boardId) {
        Long userId = authProvider.getUserIdFromPrincipal();
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new CommonApiException(CommonErrorCode.BOARD_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(()-> new CommonApiException(CommonErrorCode.USER_NOT_FOUND));
        BoardLike boardLike = boardLikeRepository.findByUserIdAndBoardId(userId, boardId).orElseThrow(()->new CommonApiException(CommonErrorCode.BOOKMARK_NOT_FOUND));

        boardLikeRepository.deleteByUserIdAndBoardId(userId, boardId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkBoardLike(Long boardId) {
        Long userId = authProvider.getUserIdFromPrincipal();
        return boardLikeRepository.existsBoardLikeByUserIdAndBoardId(userId, boardId);
    }
}
