package com.booksphillic.controller;

import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponse;
import com.booksphillic.service.board.PostService;
import com.booksphillic.service.editor.EditorService;
import com.booksphillic.service.editor.dto.GetEditorPostsRes;
import com.booksphillic.service.editor.dto.GetEditorRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/editor")
@RequiredArgsConstructor
public class EditorController {

    private final EditorService editorService;
    private final PostService postService;

    /***
     * GET /api/editor/:editorId
     * 에디터 정보 조회
     */
    @GetMapping("/{editorId}")
    public BaseResponse<GetEditorRes> getEditor(@PathVariable Long editorId) {

        try{
            GetEditorRes getEditorRes = editorService.getEditor(editorId);
            return new BaseResponse<>(getEditorRes);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }

    /***
     * GET /api/editor/:editorId/post
     * 에디터가 작성한 글 조회
     */
    @GetMapping("/{editorId}/post")
    public BaseResponse<List<GetEditorPostsRes>> getEditorPosts(@PathVariable Long editorId,
                                                      @RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "6") int size) {

        try{
            List<GetEditorPostsRes> editorPosts = postService.getEditorPosts(editorId, page, size);
            return new BaseResponse<>(editorPosts);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }
}
