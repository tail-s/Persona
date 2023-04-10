package com.ssafy.project.api.service;

import com.ssafy.project.common.db.dto.request.ParticipantAddReqDTO;

public interface ParticipantService {

    Long addParticipant(ParticipantAddReqDTO participantAddReqDTO);


}
