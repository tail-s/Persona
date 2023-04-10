package com.ssafy.project.api.service;

import com.ssafy.project.common.db.dto.response.ScriptListResDTO;
import org.springframework.data.domain.Page;

public interface BookmarkService {

    void addBookmark(Long scriptId);
    void removeBookmark(Long scriptId);
    boolean checkBookmark(Long scripId);
    Page<ScriptListResDTO> findMyBookmark(int page);
}
