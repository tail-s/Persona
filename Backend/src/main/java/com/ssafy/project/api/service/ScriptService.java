package com.ssafy.project.api.service;

import com.ssafy.project.common.db.dto.request.ScriptSearchReqDTO;
import com.ssafy.project.common.db.dto.response.ScriptDetailResDTO;
import com.ssafy.project.common.db.dto.response.ScriptListResDTO;
import org.springframework.data.domain.Page;

public interface ScriptService {

    public Page<ScriptListResDTO> findAllScript(ScriptSearchReqDTO scriptSearchReqDTO);
    public ScriptDetailResDTO detailScript(Long scriptId);
}
