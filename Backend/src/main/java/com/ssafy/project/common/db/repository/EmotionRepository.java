package com.ssafy.project.common.db.repository;

import com.ssafy.project.common.db.entity.common.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmotionRepository extends JpaRepository<Emotion, Long> {
}
