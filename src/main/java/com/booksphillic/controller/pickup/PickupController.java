package com.booksphillic.controller.pickup;

import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.bookstore.DistrictType;
import com.booksphillic.domain.pickup.PickupStatus;
import com.booksphillic.repository.pickup.PickupRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponse;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.bookstore.dto.BookstoreListRes;
import com.booksphillic.service.pickup.PickupService;
import com.booksphillic.service.pickup.dto.ApplyPickupReq;
import com.booksphillic.service.pickup.dto.ApplyPickupRes;
import com.booksphillic.service.pickup.dto.PickupBookstoreDetailRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pick-up")
@RequiredArgsConstructor
public class PickupController {

    private final PickupService pickupService;

    private final PickupRepository pickupRepository;

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

            return new BaseResponse<>(applyPickupRes);
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
    public BaseResponse<List<BookstoreListRes>> getStoreListByDistrict(@RequestParam(name = "district") String district) {
        try {
            List<Bookstore> bookstores;
            List<BookstoreListRes> result;

            if (checkDistrict(district)) {
                bookstores = pickupService.findByDistrict(district);
                result = bookstores.stream()
                        .map(BookstoreListRes::new)
                        .collect(Collectors.toList());
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

}
