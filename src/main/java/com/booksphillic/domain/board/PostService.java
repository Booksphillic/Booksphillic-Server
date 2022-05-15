package com.booksphillic.domain.board;

import com.booksphillic.domain.board.model.GetPostsRes;
import com.booksphillic.domain.response.BaseException;
import com.booksphillic.domain.response.BaseResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;

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
}
