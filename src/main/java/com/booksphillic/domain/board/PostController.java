package com.booksphillic.domain.board;

import com.booksphillic.domain.DistrictType;
import com.booksphillic.domain.board.model.GetPostsRes;
import com.booksphillic.domain.response.BaseException;
import com.booksphillic.domain.response.BaseResponse;
import com.booksphillic.domain.response.BaseResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("")
    @ResponseBody
    public BaseResponse<List<GetPostsRes>> getPosts(@RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(required = false) String include,
                                                    @RequestParam(required = false) String exclude) {
        try{
            List<GetPostsRes> getPostsRes = new ArrayList<>();
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

            return new BaseResponse<>(getPostsRes);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }

    public boolean checkDistrict(String district) {
        for(DistrictType dt : DistrictType.values()) {
            if(district.equals(dt.getEn())) return true;
        }
        return false;
    }
}
