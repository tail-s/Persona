package com.ssafy.project.api.service;

import com.ssafy.project.common.db.dto.request.SpeechAddReqDTO;
import com.ssafy.project.common.db.entity.common.Participant;
import com.ssafy.project.common.db.entity.common.Speech;
import com.ssafy.project.common.db.repository.ParticipantRepository;
import com.ssafy.project.common.security.exception.CommonApiException;
import com.ssafy.project.common.util.constant.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SpeechServiceImpl implements SpeechService {

    private final ParticipantRepository participantRepository;

    @Override
    public void addSpeech(@RequestBody SpeechAddReqDTO speechAddReqDTO) {
        Participant participant = participantRepository.findById(speechAddReqDTO.getParticipantId()).orElseThrow(() -> new CommonApiException(CommonErrorCode.PARTICIPANT_NOT_FOUND));

        Speech speech = Speech.builder()
                .participant(participant)
                .speechRecognition(speechAddReqDTO.getSpeechRecognition())
                .build();

        participant.getSpeeches().add(speech);
    }
}
