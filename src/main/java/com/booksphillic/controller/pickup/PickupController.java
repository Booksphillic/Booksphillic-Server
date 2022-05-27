package com.booksphillic.controller.pickup;

import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.bookstore.DistrictType;
import com.booksphillic.domain.bookstore.Tag;
import com.booksphillic.domain.pickup.Pickup;
import com.booksphillic.domain.pickup.PickupStatus;
import com.booksphillic.domain.user.UserPickupReviewCount;
import com.booksphillic.repository.pickup.PickupRepository;
import com.booksphillic.repository.user.UserPRCountRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponse;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.bookstore.dto.BookstoreListRes;
import com.booksphillic.service.pickup.PickupReviewService;
import com.booksphillic.service.pickup.PickupService;
import com.booksphillic.service.pickup.dto.*;
import com.booksphillic.service.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pick-up")
@RequiredArgsConstructor
public class PickupController {

    private final PickupService pickupService;
    private final PickupReviewService pickupReviewService;
    private final PickupRepository pickupRepository;
    private final UserPRCountRepository countRepository;
    private final TagService tagService;

    @PostMapping("/apply")
    public BaseResponse<ApplyPickupRes> applyPickup(@RequestBody ApplyPickupReq applyPickupReq) {
        try {
            Long userId = applyPickupReq.getUserId();
            Long storeId = applyPickupReq.getStoreId();
            String bookGenre = applyPickupReq.getBookGenre();
            LocalDateTime pickupDate = applyPickupReq.getPickupDate();
            PickupStatus status = applyPickupReq.getStatus();
            String requirements = applyPickupReq.getRequirements();

            ApplyPickupRes applyPickupRes = pickupService.postPickup(userId, storeId, bookGenre, pickupDate, status, requirements);

            UserPickupReviewCount count = countRepository.findByUserId(userId).get();
            count.setPickupCount(count.getPickupCount()+1);
            count.setPhillic(count.getPhillic()+10);
            countRepository.save(count);
            return new BaseResponse<>(applyPickupRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }

    /**
     * 유저별 픽업 조회
     */
    @GetMapping("/{userId}")
    public BaseResponse<List<PickupListRes>> getPickupByUser(@PathVariable Long userId) {

        try {
            List<PickupListRes> pickups = pickupService.getPickupByUser(userId);

            return new BaseResponse<>(pickups);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }



    /**
     * 지역구별 책방 조회
     * @param district
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<PickupBookstoreListRes>> getStoreListByDistrict(@RequestParam(name = "district") String district) {
        try {
            List<Bookstore> bookstores;
            List<PickupBookstoreListRes> result;

            if (checkDistrict(district)) {
                bookstores = pickupService.findByDistrict(district);
                result = bookstores.stream().map(store -> {
                    try {
                        List<Tag> tags = tagService.getStoreTags(store.getId());
                        return new PickupBookstoreListRes(store, tags);
                    } catch (BaseException e) {
                        e.printStackTrace();
                        return null;
                    }}
                ).collect(Collectors.toList());

                return new BaseResponse<>(result);
            } else {
                return new BaseResponse<>(BaseResponseCode.INVALID_DISTRICT);
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
    public BaseResponse<PickupBookstoreDetailRes> getPickupStoreDetail(@RequestParam Long storeId) {
        try {
            PickupBookstoreDetailRes result = pickupService.findById(storeId);

            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }


    /**
     * 미스터리북 리뷰 작성 이미지 등록 (최대 3장)
     */
    @PostMapping("/review/images")
    public BaseResponse<List<String>> postPickupReviewImages(@RequestParam List<MultipartFile> files) {
        try {
            List<String> urls = pickupReviewService.postPickupReviewImages(files);
            return new BaseResponse<>(urls);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }

    /**
     * 미스터리북 리뷰 등록 (이미지 url 포함)
     */
    @PostMapping("/{pickupId}/review")
    public BaseResponse<PickupReviewRes> postPickupReview(@PathVariable Long pickupId, @RequestBody PickupReviewReq pickupReviewReq) {
        try {
            Long userId = pickupReviewReq.getUserId();
            String content = pickupReviewReq.getContent();
            List<String> urls = pickupReviewReq.getUrls();
            String emoticon = pickupReviewReq.getEmoticon();

            PickupReviewRes pickupReviewRes = pickupReviewService.postReview(pickupId, userId, content, emoticon, urls);
            return new BaseResponse<>(pickupReviewRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }

    }

//    @GetMapping("/{pickupId}/reviewList")
//    public BaseResponse<List<PickupReviewListRes>> getPickupReviews(@PathVariable Long pickupId) {
//        try {
//            List<PickupReviewListRes> result = pickupReviewService.getPickupReviews(pickupId);
//            return new BaseResponse<>(result);
//        } catch (BaseException e) {
//            return new BaseResponse<>(e.getCode());
//        }
//    }

    @GetMapping("/{storeId}/reviewList")
    public BaseResponse<List<PickupReviewListRes>> getPickupReviewsByBookstore(@PathVariable Long storeId) {
        try {
            List<PickupReviewListRes> result = pickupReviewService.getPickupReviewsByBookstore(storeId);
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }
}
