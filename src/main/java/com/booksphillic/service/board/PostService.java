package com.booksphillic.service.board;

import com.booksphillic.domain.board.Comment;
import com.booksphillic.domain.board.Post;
import com.booksphillic.repository.PostJpaRepository;
import com.booksphillic.repository.CommentRepository;
import com.booksphillic.repository.PostRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostJpaRepository postJpaRepository;

    public List<GetPostsRes> getDistrictPosts(int page, int size, boolean include, String district) throws BaseException {

        int offset = (page-1)*size;
        try{
            List<GetPostsRes> getPostsRes;
            if(include) {
                getPostsRes = postRepository.selectSameDistrictPosts(district, offset, size);
            } else {
                getPostsRes = postRepository.selectOtherDistrictPosts(district, offset, size);
            }
            return getPostsRes;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }

    // 글 상세 조회
    public Post getPost(Long postId) throws BaseException {
        Post post = postJpaRepository.findById(postId).get();

        if(post == null) // 존재하지 않는 아이디
            throw new BaseException(BaseResponseCode.INVALID_POSTID);
        else return post;

    }

    // 댓글 조회
    public List<Comment> getComments(Long postId) throws BaseException {
//        Post post = postRepository.findById(postId).get();
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments;
    }
}
