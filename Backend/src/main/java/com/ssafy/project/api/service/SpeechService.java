package com.ssafy.project.api.service;

import com.ssafy.project.common.db.dto.request.SpeechAddReqDTO;

public interface SpeechService {

    public void addSpeech(SpeechAddReqDTO speechAddReqDTO);
}
