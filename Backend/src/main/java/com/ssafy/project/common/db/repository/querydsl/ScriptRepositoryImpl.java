package com.ssafy.project.common.db.repository.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.project.common.db.dto.request.ScriptSearchReqDTO;
import com.ssafy.project.common.db.dto.response.ScriptDetailResDTO;
import com.ssafy.project.common.db.dto.response.ScriptListResDTO;
import com.ssafy.project.common.db.entity.base.EmotionEnum;
import com.ssafy.project.common.db.entity.base.GenreEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.ssafy.project.common.db.entity.common.QScript.script;

@RequiredArgsConstructor
public class ScriptRepositoryImpl implements ScriptRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ScriptListResDTO> findAllWithFilter(ScriptSearchReqDTO scriptSearchReqDTO) {

        Pageable pageable = PageRequest.of(scriptSearchReqDTO.getPage(), 10);
        QueryResults<ScriptListResDTO> results = queryFactory
                .select(Projections.constructor(ScriptListResDTO.class
                        , script.id
                        , script.title
                        , script.author
                        , script.actor
                        , script.viewCnt
                        , script.emotion
                        , script.genre
                        , script.createdDate
                        , script.bookmarks.size()
                        , script.participants.size()
                ))
                .from(script)
                .where(emotionFilter(scriptSearchReqDTO.getEmotions())
                        , genreFilter(scriptSearchReqDTO.getGenres())
                        , keywordFilter(scriptSearchReqDTO.getOption(), scriptSearchReqDTO.getKeyword()))
                .orderBy(makeOrder(scriptSearchReqDTO.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private OrderSpecifier<?> makeOrder(String sort) {
        if(sort.equals("인기순")) return script.bookmarks.size().desc();
        else if(sort.equals("참여순")) return script.participants.size().desc();
        else if(sort.equals("조회순")) return script.viewCnt.desc();
        else return script.id.desc();
    }

    private BooleanExpression keywordFilter(String option, String keyword) {
        if(option == null || keyword == null) return null;
        if(option.equals("content")) return script.content.contains(keyword);
        if(option.equals("title")) return script.title.contains(keyword);
        if(option.equals("author")) return script.author.contains(keyword);
        if(option.equals("actor")) return script.author.contains(keyword);
        else return null;
    }

    private BooleanBuilder emotionFilter(List<String> emotions) {
        System.out.println("emotions size = " + emotions.size());
        if(emotions.size() == 0) return null;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for(String emotion : emotions){
            booleanBuilder.or(script.emotion.eq(EmotionEnum.valueOf(emotion)));
        }
        return booleanBuilder;
    }

    private BooleanBuilder genreFilter(List<String> genres) {
        System.out.println("genres size = " + genres.size());
        if(genres.size() == 0) return null;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for(String genre : genres){
            booleanBuilder.or(script.genre.eq(GenreEnum.valueOf(genre)));
        }
        return booleanBuilder;
    }
}
