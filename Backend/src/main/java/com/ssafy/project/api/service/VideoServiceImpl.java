package com.ssafy.project.api.service;

import com.ssafy.project.common.db.dto.request.VideoAddReqDTO;
import com.ssafy.project.common.db.dto.response.VideoDetailResDTO;
import com.ssafy.project.common.db.dto.response.VideoListResDTO;
import com.ssafy.project.common.db.entity.common.Participant;
import com.ssafy.project.common.db.entity.common.Script;
import com.ssafy.project.common.db.entity.common.User;
import com.ssafy.project.common.db.entity.common.Video;
import com.ssafy.project.common.db.repository.ParticipantRepository;
import com.ssafy.project.common.db.repository.ScriptRepository;
import com.ssafy.project.common.db.repository.UserRepository;
import com.ssafy.project.common.db.repository.VideoRepository;
import com.ssafy.project.common.provider.AuthProvider;
import com.ssafy.project.common.provider.S3Provider;
import com.ssafy.project.common.security.exception.CommonApiException;
import com.ssafy.project.common.util.constant.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final ScriptRepository scriptRepository;
    private final AuthProvider authProvider;
//    private final S3Provider s3Provider;
//    private final S3Utils s3Utils;

    @Transactional
    @Override
    public void saveVideo(VideoAddReqDTO videoAddReqDTO) {

        Participant participant = participantRepository.findById(videoAddReqDTO.getParticipantId())
                .orElseThrow(() -> new CommonApiException(CommonErrorCode.PARTICIPANT_NOT_FOUND));

        User user = userRepository.findById(authProvider.getUserIdFromPrincipal())
                .orElseThrow(() -> new CommonApiException(CommonErrorCode.USER_NOT_FOUND));

//        List<String> uris;

//        uris = s3Utils.upload(videoCreateReqDTO.getVideoFile(), videoCreateReqDTO.getGraphFile());

        videoRepository.save(Video.builder()
                .title(videoAddReqDTO.getTitle())
                .videoUrl(videoAddReqDTO.getVideoUrl())
                .thumbnailUrl(videoAddReqDTO.getThumbnailUrl())
                .graphUrl(videoAddReqDTO.getGraphUrl())
                .analysis(videoAddReqDTO.getAnalysis())
                .user(user)
                .participant(participant)
                .build());
    }

    @Transactional
    @Override
    public void deleteVideo(Long videoId) {

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new CommonApiException(CommonErrorCode.VIDEO_NOT_FOUND));

        if (!(video.getUser().getId() == authProvider.getUserIdFromPrincipal())) {
            throw new CommonApiException(CommonErrorCode.VIDEO_NOT_ALLOWED);
        }

//        s3Provider.delete(video.getVideoUrl());
//        s3Provider.delete(video.getThumbnailUrl());
//        s3Provider.delete(video.getGraphUrl());

        videoRepository.delete(video);
    }

    @Transactional
    @Override
    public Page<VideoListResDTO> findAllVideo(int page) {

        final int SIZE = 10;
        final String SORT = "createdDate";

        Page<Video> videos = videoRepository.findAllByUserId(authProvider.getUserIdFromPrincipal(),
                PageRequest.of(page, SIZE, Sort.by(SORT).descending()));

        return videos.map(video -> VideoListResDTO.builder()
                .id(String.valueOf(video.getId()))
                .title(video.getTitle())
                .thumbnailUrl(video.getThumbnailUrl())
                .analysis(video.getAnalysis())
                .createdDate(video.getCreatedDate().toString())
                .build());
    }

    @Transactional
    @Override
    public VideoDetailResDTO detailVideo(Long videoId) {

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new CommonApiException(CommonErrorCode.VIDEO_NOT_FOUND));

        if (!(video.getUser().getId() == authProvider.getUserIdFromPrincipal())) {
            throw new CommonApiException(CommonErrorCode.VIDEO_NOT_ALLOWED);
        }

        Script script = scriptRepository.findByParticipantsId(video.getParticipant().getId())
                .orElseThrow(() -> new CommonApiException(CommonErrorCode.SCRIPT_NOT_FOUND));

        return VideoDetailResDTO.builder()
                .id(String.valueOf(video.getId()))
                .title(video.getTitle())
                .emotion(script.getEmotion().name())
                .genre(script.getGenre().name())
                .actor(script.getActor())
                .author(script.getAuthor())
                .videoUrl(video.getVideoUrl())
                .graphUrl(video.getGraphUrl())
                .analysis(video.getAnalysis())
                .createdDate(video.getCreatedDate().toString())
                .build();
    }
}
