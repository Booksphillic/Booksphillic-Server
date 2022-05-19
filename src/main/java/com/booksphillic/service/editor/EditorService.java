package com.booksphillic.service.editor;

import com.booksphillic.domain.board.Editor;
import com.booksphillic.repository.EditorRepository;
import com.booksphillic.repository.PostJpaRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.editor.dto.GetEditorPostsRes;
import com.booksphillic.service.editor.dto.GetEditorRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class EditorService {

    private final EditorRepository editorRepository;
    private final PostJpaRepository postJpaRepository;


    // 에디터 정보 조회
    public GetEditorRes getEditor(Long editorId) throws BaseException {
        try{
            Editor editor = editorRepository.findById(editorId).get();
            if(editor == null) {
                throw new BaseException(BaseResponseCode.INVALID_EDITOR_ID);
            }

            long postCount = postJpaRepository.countByEditorId(editorId);

            return GetEditorRes.builder()
                    .editorName(editor.getName())
                    .description(editor.getDescription())
                    .postCount(postCount)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }

}
