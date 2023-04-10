package com.ssafy.project.common.db.repository;

import com.ssafy.project.common.db.entity.common.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Book;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    void deleteByUserIdAndScriptId(Long userId, Long scriptId);
    boolean existsScriptByUserIdAndScriptId(Long userId, Long scriptId);
    Page<Bookmark> findByUserId(Long userId, Pageable pageable);
}
