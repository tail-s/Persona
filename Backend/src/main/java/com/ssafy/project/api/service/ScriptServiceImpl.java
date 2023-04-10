package com.ssafy.project.api.service;

import com.ssafy.project.common.db.dto.request.ScriptSearchReqDTO;
import com.ssafy.project.common.db.dto.response.ScriptDetailResDTO;
import com.ssafy.project.common.db.dto.response.ScriptListResDTO;
import com.ssafy.project.common.db.entity.common.Script;
import com.ssafy.project.common.db.repository.ScriptRepository;
import com.ssafy.project.common.security.exception.CommonApiException;
import com.ssafy.project.common.util.constant.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ScriptServiceImpl implements ScriptService{

    private final ScriptRepository scriptRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ScriptListResDTO> findAllScript(ScriptSearchReqDTO scriptSearchReqDTO) {
        Page<ScriptListResDTO> scripts = scriptRepository.findAllWithFilter(scriptSearchReqDTO);
        return scripts;
    }

    @Override
    public ScriptDetailResDTO detailScript(Long scriptId) {

        Script script = scriptRepository.findById(scriptId).orElseThrow(() -> new CommonApiException(CommonErrorCode.SCRIPT_NOT_FOUND));
        script.setViewCnt(script.getViewCnt()+1L);

        ScriptDetailResDTO scriptDetailResDTO = ScriptDetailResDTO.builder()
                .id(script.getId())
                .title(script.getTitle())
                .author(script.getAuthor())
                .actor(script.getActor())
                .content(script.getContent())
                .viewCnt(script.getViewCnt())
                .emotion(script.getEmotion())
                .genre(script.getGenre())
                .createdDate(script.getCreatedDate())
                .bookmarkCnt(script.getBookmarks().size())
                .participantCnt(script.getParticipants().size())
                .build();

        return scriptDetailResDTO;
    }
}
