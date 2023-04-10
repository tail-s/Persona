package com.ssafy.project.api.service;

import com.ssafy.project.common.db.dto.response.ScriptListResDTO;
import com.ssafy.project.common.db.entity.common.Bookmark;
import com.ssafy.project.common.db.entity.common.Script;
import com.ssafy.project.common.db.entity.common.User;
import com.ssafy.project.common.db.repository.BookmarkRepository;
import com.ssafy.project.common.db.repository.ScriptRepository;
import com.ssafy.project.common.db.repository.UserRepository;
import com.ssafy.project.common.provider.AuthProvider;
import com.ssafy.project.common.security.exception.CommonApiException;
import com.ssafy.project.common.util.constant.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final ScriptRepository scriptRepository;
    private final AuthProvider authProvider;

    @Override
    public void addBookmark(Long scriptId) {
        Long userId = authProvider.getUserIdFromPrincipal();
        User user = userRepository.findById(userId).orElseThrow(() -> new CommonApiException(CommonErrorCode.USER_NOT_FOUND));
        Script script = scriptRepository.findById(scriptId).orElseThrow(() -> new CommonApiException(CommonErrorCode.SCRIPT_NOT_FOUND));

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .script(script)
                .build();

        user.getBookmarks().add(bookmark);
    }

    @Override
    public void removeBookmark(Long scriptId) {
        Long userId = authProvider.getUserIdFromPrincipal();
        if(!bookmarkRepository.existsScriptByUserIdAndScriptId(userId, scriptId)) throw new CommonApiException(CommonErrorCode.BOOKMARK_NOT_FOUND);
        bookmarkRepository.deleteByUserIdAndScriptId(userId, scriptId);
    }

    @Override
    public boolean checkBookmark(Long scriptId) {
        Long userId = authProvider.getUserIdFromPrincipal();
        return bookmarkRepository.existsScriptByUserIdAndScriptId(userId, scriptId);
    }

    @Override
    public Page<ScriptListResDTO> findMyBookmark(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<Bookmark> bookmarks = bookmarkRepository.findByUserId(authProvider.getUserIdFromPrincipal(), pageable);
        Page<ScriptListResDTO> scripts = bookmarks.map(bookmark ->
                ScriptListResDTO.builder()
                        .id(bookmark.getScript().getId())
                        .title(bookmark.getScript().getTitle())
                        .author(bookmark.getScript().getAuthor())
                        .actor(bookmark.getScript().getActor())
                        .viewCnt(bookmark.getScript().getViewCnt())
                        .emotion(bookmark.getScript().getEmotion())
                        .genre(bookmark.getScript().getGenre())
                        .createdDate(bookmark.getScript().getCreatedDate())
                        .bookmarkCnt(bookmark.getScript().getBookmarks().size())
                        .participantCnt(bookmark.getScript().getParticipants().size())
                        .build());
        return scripts;
    }
}
