package com.ssafy.project.common.db.entity.common;

import com.ssafy.project.common.db.entity.base.BaseTime;
import com.ssafy.project.common.db.entity.base.EmotionEnum;
import com.ssafy.project.common.db.entity.base.GenreEnum;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Script extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "script_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private EmotionEnum emotion;

    @Enumerated(EnumType.STRING)
    private GenreEnum genre;

    private String title;

    private String author;

    private String actor;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long viewCnt;

    @Builder.Default
    @OneToMany(mappedBy = "script")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "script", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants = new ArrayList<>();
}
