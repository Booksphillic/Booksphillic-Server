package com.booksphillic.service.board;

import com.booksphillic.domain.board.Comment;
import com.booksphillic.domain.board.Editor;
import com.booksphillic.domain.board.Post;
import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.bookstore.BookstoreTag;
import com.booksphillic.domain.bookstore.Tag;
import com.booksphillic.repository.BookstoreTagRepository;
import com.booksphillic.repository.PostJpaRepository;
import com.booksphillic.repository.CommentRepository;
import com.booksphillic.repository.PostRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostJpaRepository postJpaRepository;
    private final BookstoreTagRepository bookstoreTagRepository;

    // 지역구별 글 리스트 조회
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
    public GetPostRes getPost(Long postId) throws BaseException {
        try{
            Post post = postJpaRepository.findById(postId).get();

            if(post == null) // 존재하지 않는 아이디
                throw new BaseException(BaseResponseCode.INVALID_POSTID);

            Bookstore bookstore = post.getBookstore();
            Editor editor = post.getEditor();

            // 태그 조회
            List<String> tags = bookstoreTagRepository.findByStoreId(bookstore.getId())
                    .stream().map(bt -> bt.getTag().getName()).collect(Collectors.toList());

            return GetPostRes.builder()
                    .postId(postId)
                    .editorName(editor.getName())
                    .editorImage(editor.getProfileImgUrl())
                    .createdAt(post.getCreateAt())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .bookstore(new BookstoreInfo(bookstore))
                    .tagList(tags)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }

    }


    // 댓글 조회
    public List<GetCommentsRes> getComments(Long postId) throws BaseException {
        try{
            List<Comment> comments = commentRepository.findByPostId(postId);
            List<GetCommentsRes> commentsRes = comments.stream().map(c -> GetCommentsRes.builder()
                            .content(c.getContent())
                            .createdAt(c.getCreateAt())
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
}
