package com.ssafy.project.common.db.entity.base;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
// @MappedSuperclass vs @Embeddable => https://blog.naver.com/PostView.nhn?blogId=qjawnswkd&logNo=222074957987
@MappedSuperclass
// @LastModifiedDate 위해 Auditing 기능 포함
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime {

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
}