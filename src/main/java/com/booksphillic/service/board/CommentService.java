package com.booksphillic.service.board;

import com.booksphillic.domain.board.Comment;
import com.booksphillic.domain.user.User;
import com.booksphillic.repository.CommentRepository;
import com.booksphillic.repository.UserRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.board.dto.GetCommentsRes;
import com.booksphillic.service.board.dto.PostCommentReq;
import com.booksphillic.service.board.dto.PostCommentRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // 댓글 조회
    public List<GetCommentsRes> getComments(Long postId) throws BaseException {
        try{
            List<Comment> comments = commentRepository.findByPostId(postId);
            List<GetCommentsRes> commentsRes = comments.stream().map(c -> GetCommentsRes.builder()
                            .content(c.getContent())
                            .createdAt(c.getCreatedAt())
                            .username(c.getUser().getUsername())
                            .userProfileImgUrl(c.getUser().getProfileImgUrl())
                            .build())
                    .collect(Collectors.toList());
            return commentsRes;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }

    // 댓글 작성
    public PostCommentRes postComment(Long postId, PostCommentReq postCommentReq) throws BaseException {
        try{
            Long userId = postCommentReq.getUserId();
            User user = checkUserId(userId);
            if(user == null) { // 유효하지 않은 userid
                throw new BaseException(BaseResponseCode.INVALID_USER_ID);
            }

            Comment savedComment = commentRepository.save(Comment.builder()
                    .postId(postId)
                    .user(user)
                    .content(postCommentReq.getComment())
                    .build());
            return PostCommentRes.builder().commentId(savedComment.getId()).build();

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }

    private User checkUserId(Long userId) {
        User user = userRepository.findById(userId).get();
        if(user == null) return null;
        else return user;
    }

}
