package com.ssafy.project.api.service;

import com.ssafy.project.common.db.dto.request.EmotionAddReqDTO;
import com.ssafy.project.common.db.entity.common.Emotion;
import com.ssafy.project.common.db.entity.common.Participant;
import com.ssafy.project.common.db.repository.ParticipantRepository;
import com.ssafy.project.common.security.exception.CommonApiException;
import com.ssafy.project.common.util.constant.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmotionServiceImpl implements EmotionService {

    private final ParticipantRepository participantRepository;

    @Override
    public void addEmotion(EmotionAddReqDTO emotionAddReqDTO) {
        Participant participant = participantRepository.findById(emotionAddReqDTO.getParticipantId()).orElseThrow(() -> new CommonApiException(CommonErrorCode.PARTICIPANT_NOT_FOUND));

        Emotion emotion = Emotion.builder()
                .participant(participant)
                .time(emotionAddReqDTO.getTime())
                .angry(emotionAddReqDTO.getPleasure())
                .disgust(emotionAddReqDTO.getEmbarrassed())
                .fear(emotionAddReqDTO.getAnger())
                .happy(emotionAddReqDTO.getAnxiety())
                .sad(emotionAddReqDTO.getHurt())
                .surprise(emotionAddReqDTO.getSad())
                .neutral(emotionAddReqDTO.getNeutrality())
                .build();

        participant.getEmotions().add(emotion);
    }
}
