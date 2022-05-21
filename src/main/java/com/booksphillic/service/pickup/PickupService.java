package com.booksphillic.service.pickup;

import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.pickup.BookGenre;
import com.booksphillic.domain.pickup.Pickup;
import com.booksphillic.domain.pickup.PickupStatus;
import com.booksphillic.domain.user.User;
import com.booksphillic.repository.UserRepository;
import com.booksphillic.repository.bookstore.BookstoreRepository;
import com.booksphillic.repository.pickup.PickupJpaRepository;
import com.booksphillic.repository.pickup.PickupRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.pickup.dto.ApplyPickupRes;
import com.booksphillic.service.pickup.dto.PickupBookstoreDetailRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PickupService {

    private final PickupRepository pickupRepository;

    private final UserRepository userRepository;

    private final PickupJpaRepository pickupJpaRepository;
    private final BookstoreRepository bookstoreRepository;

    @Transactional
    public ApplyPickupRes postPickup(Long userId, Long storeId, BookGenre bookGenre, LocalDateTime pickupDate, String status, String requirements) throws BaseException {
        try {
            User user = checkUserId(userId);
            if(user == null) {
                throw new BaseException(BaseResponseCode.INVALID_USER_ID);
            }
            Bookstore bookstore = bookstoreRepository.findById(storeId);
            if(bookstore == null) {
                throw new BaseException(BaseResponseCode.INVALID_BOOKSTOREID);
            }

            Pickup pickup = Pickup.builder()
                    .userId(userId)
                    .storeId(storeId)
                    .bookGenre(BookGenre.valueOf(bookGenre.getEn()))
                    .pickupDate(pickupDate)
                    .pickupStatus(PickupStatus.NEW)
                    .requirements(requirements)
                    .build();

            pickupJpaRepository.save(pickup);

            return ApplyPickupRes.builder()
                    .bookstoreName(bookstoreRepository.findById(storeId).getName())
                    .pickupDate(pickupDate)
                    .bookGenres(BookGenre.valueOf(bookGenre.getKo()))
                    .requirements(requirements).build();

        } catch (BaseException e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }

    private User checkUserId(Long userId) throws IllegalArgumentException {
        User user = userRepository.findById(userId)
                .orElseThrow(IllegalArgumentException::new);

        return user;
    }

    @Transactional
    public List<Bookstore> findByDistrict(String district) throws BaseException {
        try {
            return pickupRepository.findByDistrict(district);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }

    public PickupBookstoreDetailRes findById(Long id) throws BaseException {
        try {
            Bookstore bookstore = pickupRepository.findById(id);

            if (bookstore == null) {
                throw new BaseException(BaseResponseCode.INVALID_BOOKSTOREID);
            } else {
                return new PickupBookstoreDetailRes(bookstore);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }

}
