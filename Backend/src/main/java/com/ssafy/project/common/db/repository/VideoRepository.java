package com.ssafy.project.common.db.repository;

import com.ssafy.project.common.db.entity.common.Participant;
import com.ssafy.project.common.db.entity.common.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    Page<Video> findAllByUserId(Long userId, Pageable pageable);
}
