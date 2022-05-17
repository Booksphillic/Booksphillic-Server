package com.booksphillic.controller.bookstore;

import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.bookstore.DistrictType;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponse;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.bookstore.BookstoreDetailRes;
import com.booksphillic.service.bookstore.BookstoreService;
import com.booksphillic.service.bookstore.BookstoreListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookstore")
@RequiredArgsConstructor
public class BookstoreController {

    private final BookstoreService bookstoreService;

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
}
