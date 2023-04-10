package com.ssafy.project.api.service;

import com.ssafy.project.common.db.dto.request.VideoAddReqDTO;
import com.ssafy.project.common.db.dto.response.VideoDetailResDTO;
import com.ssafy.project.common.db.dto.response.VideoListResDTO;
import org.springframework.data.domain.Page;

public interface VideoService {
    void saveVideo(VideoAddReqDTO videoCreateReqDTO);
    void deleteVideo(Long videoId);
    Page<VideoListResDTO> findAllVideo(int page);
    VideoDetailResDTO detailVideo(Long videoId);
}
