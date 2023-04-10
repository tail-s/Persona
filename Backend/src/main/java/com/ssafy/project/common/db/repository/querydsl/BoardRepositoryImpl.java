package com.ssafy.project.common.db.repository.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.project.common.db.dto.response.BoardAllResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.ssafy.project.common.db.entity.common.QBoard.board;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<BoardAllResDTO> findAllWithFilter(int page, String sort, String keyword) {
        Pageable pageable = PageRequest.of(page, 10);
        QueryResults<BoardAllResDTO> results = queryFactory
                .select(Projections.constructor(BoardAllResDTO.class
                        , board.id
                        , board.user.nickname
                        , board.user.email
                        , board.createdDate
                        , board.title
                        , board.content
                        , board.viewCnt
                        , board.boardLikes.size()
                        , board.comments.size()
                        ))
                .from(board)
                .where(searchCondition(keyword))
                .orderBy(makeOrder(sort))
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private BooleanExpression searchCondition(String keyword) {
        if(keyword == null) return null;
        return board.user.nickname.contains(keyword)
                .or(board.title.contains(keyword))
                .or(board.content.contains(keyword));
    }

    private OrderSpecifier<?> makeOrder(String sort) {
        if(sort == null) return board.id.desc();
        if(sort.equals("좋아요순")) return board.boardLikes.size().desc();
        else if(sort.equals("조회순")) return board.viewCnt.desc();
        else if(sort.equals("댓글수순")) return board.comments.size().desc();
        else return board.id.desc();
    }

    @Override
    public List<BoardAllResDTO> findTop3Board() {

        QueryResults<BoardAllResDTO> results = queryFactory
                .select(Projections.constructor(BoardAllResDTO.class
                        , board.id
                        , board.user.nickname
                        , board.user.email
                        , board.createdDate
                        , board.title
                        , board.content
                        , board.viewCnt
                        , board.boardLikes.size()
                        , board.comments.size()
                ))
                .from(board)
                .orderBy(board.boardLikes.size().desc())
                .limit(3)
                .fetchResults();

        return results.getResults();
    }
}
