package com.ssafy.project.common.db.repository;

import com.ssafy.project.common.db.entity.common.Speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeechRepository extends JpaRepository<Speech, Long> {
}
