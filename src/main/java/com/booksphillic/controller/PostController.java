package com.booksphillic.controller;

import com.booksphillic.service.board.CommentService;
import com.booksphillic.service.board.dto.*;
import com.booksphillic.domain.bookstore.DistrictType;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponse;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.board.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    /**
     * GET /api/board
     * 게시글 리스트 (동네 컬렉션) 조회
     */
    @GetMapping("")
    @ResponseBody
    public BaseResponse<List<GetPostsRes>> getPosts(@RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(required = false) String include,
                                                    @RequestParam(required = false) String exclude) {
        try{
            List<GetPostsRes> getPostsRes;
            if(include != null) {
                if(checkDistrict(include))  // 지역구 유효성 검사
                    getPostsRes = postService.getDistrictPosts(page, size, true, include);
                else
                    return new BaseResponse<>(BaseResponseCode.INVALID_DISTRICT);
            }
            else if(exclude != null) {
                if(checkDistrict(exclude))  // 지역구 유효성 검사
                    getPostsRes = postService.getDistrictPosts(page, size, false, exclude);
                else
                    return new BaseResponse<>(BaseResponseCode.INVALID_DISTRICT);
            }
            else { // 전체 조회
                getPostsRes = postService.getAllDistrictPosts(page, size);
            }

            return new BaseResponse<>(getPostsRes);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }

    private boolean checkDistrict(String district) {
        for(DistrictType dt : DistrictType.values()) {
            if(district.equals(dt.getEn())) return true;
        }
        return false;
    }


    /**
     * GET /api/board/:postId
     * 게시글 상세 조회
     */
    @GetMapping("/{postId}")
    @ResponseBody
    public BaseResponse<GetPostRes> getPost(@PathVariable Long postId) {
        try{
            GetPostRes getPostRes = postService.getPost(postId);
            return new BaseResponse<>(getPostRes);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }

    }

    /**
     * GET /api/board/:postId/comment
     * 게시글 하단 댓글 조회
     */
    @GetMapping("/{postId}/comment")
    @ResponseBody
    public BaseResponse<List<GetCommentsRes>> getComments(@PathVariable Long postId) {
        try {
            List<GetCommentsRes> comments = commentService.getComments(postId);
            return new BaseResponse<>(comments);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }

    /***
     * POST /api/board/:postId/comment
     * 게시글 댓글 작성
     */
    @PostMapping("/{postId}/comment")
    @ResponseBody
    public BaseResponse<PostCommentRes> postComments(@PathVariable Long postId, @RequestBody PostCommentReq postCommentReq) {
        try {
            PostCommentRes postCommentRes = commentService.postComment(postId, postCommentReq);
            return new BaseResponse<>(postCommentRes);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }
}
