package com.ssafy.project.common.db.repository.querydsl;

import com.ssafy.project.common.db.dto.response.BoardAllResDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoardRepositoryCustom {

    Page<BoardAllResDTO> findAllWithFilter(int page, String sort, String keyword);
    List<BoardAllResDTO> findTop3Board();
}
