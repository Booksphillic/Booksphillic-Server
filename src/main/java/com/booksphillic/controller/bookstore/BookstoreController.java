package com.booksphillic.controller.bookstore;

import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.bookstore.BookstoreReview;
import com.booksphillic.domain.bookstore.DistrictType;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponse;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.bookstore.BookstoreReviewService;
import com.booksphillic.service.bookstore.dto.*;
import com.booksphillic.service.bookstore.BookstoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookstore")
@RequiredArgsConstructor
public class BookstoreController {

    private final BookstoreService bookstoreService;
    private final BookstoreReviewService bookstoreReviewService;

    /**
     * 책방 프로필 전체 조회 (district=0 -> 전체 조회, !=0 -> 지역구별 조회)
     */
    @GetMapping("/list")
    public BaseResponse<List<BookstoreListRes>> getAllStoreList(@RequestParam(name = "district", defaultValue = "0") String district) {
        try {
            List<Bookstore> bookstores;
            List<BookstoreListRes> result;
            if(district.equals("0")) { // 전체 조회
                bookstores = bookstoreService.findAll();
                result = bookstores.stream()
                        .map(b -> new BookstoreListRes(b))
                        .collect(Collectors.toList());
                return new BaseResponse<>(result);
            }
            else { //지역구별 조회
                if(checkDistrict(district)) { //지역구 유효성 검사
                    bookstores = bookstoreService.findByDistrict(district);
                    result = bookstores.stream()
                            .map(b -> new BookstoreListRes(b))
                            .collect(Collectors.toList());
                    return new BaseResponse<>(result);
                }
                else { //없는 지역구
                    return new BaseResponse<>(BaseResponseCode.INVALID_DISTRICT);
                }
            }
        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }

    public boolean checkDistrict(String district) {
        for(DistrictType dt : DistrictType.values()) {
            if(district.equals(dt.getEn()))
                return true;
        }
        return false;
    }

    /**
     * 책방 프로필 상세 조회
     */
    @GetMapping("")
    public BaseResponse<BookstoreDetailRes> getStoreDetail(@RequestParam Long storeId) {
        try {
            // service에서 id로 bookstore 엔티티 조회
            BookstoreDetailRes result = bookstoreService.findById(storeId);
            // bookstore 엔티티를 dto에 맞게 변환 후 return
            return new BaseResponse<>(result);
        }catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }

    /**
     * 메인화면 책방 프로필(이름, 주소, 대표사진, 소개글?)
     */


    /**
     * 책방 리뷰 작성 이미지 등록 (최대 4장)
     */
    @PostMapping("/reviewImages")
    public BaseResponse<List<String>> postBookstoreReview(@RequestParam List<MultipartFile> files) {
        try {
            System.out.println("files.size() = " + files.size());
            List<String> urls = bookstoreReviewService.postReviewImages(files);
            return new BaseResponse<>(urls);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }

    /**
     * 책방 리뷰 등록 (이미지 url포함)
     */
    @PostMapping("/{storeId}/review")
    public BaseResponse<StoreReviewRes> postBookstoreReview(@PathVariable Long storeId, @RequestBody StoreReviewReq storeReviewReq) {
        try {
            Long userId = storeReviewReq.getUserId();
            String content = storeReviewReq.getContent();
            List<String> urls = storeReviewReq.getUrls();

            StoreReviewRes storeReviewRes = bookstoreReviewService.postReview(storeId, userId, content, urls);
            return new BaseResponse<>(storeReviewRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }

    /**
     * 책방별 리뷰 조회
     */
    @GetMapping("/{storeId}/reviewList")
    public BaseResponse<List<StoreReviewListRes>> getBookstoreReviews(@PathVariable Long storeId) {
        try {
            List<StoreReviewListRes> result = bookstoreReviewService.getReviews(storeId);
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }
}
