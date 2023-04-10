package com.ssafy.project.common.db.repository;

import com.ssafy.project.common.db.entity.common.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

}
