package com.ssafy.project.common.db.repository.querydsl;

import com.ssafy.project.common.db.dto.request.ScriptSearchReqDTO;
import com.ssafy.project.common.db.dto.response.ScriptListResDTO;
import org.springframework.data.domain.Page;

public interface ScriptRepositoryCustom {

    Page<ScriptListResDTO> findAllWithFilter(ScriptSearchReqDTO scriptSearchReqDTO);
}
