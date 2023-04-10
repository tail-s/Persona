package com.ssafy.project.common.db.repository;

import com.ssafy.project.common.db.entity.common.Board;
import com.ssafy.project.common.db.repository.querydsl.BoardRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    Page<Board> findByUserId(Long userId, Pageable pageable);
}
