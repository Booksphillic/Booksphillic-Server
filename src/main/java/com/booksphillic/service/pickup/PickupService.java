package com.booksphillic.service.pickup;

import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.user.User;
import com.booksphillic.repository.UserRepository;
import com.booksphillic.repository.bookstore.BookstoreRepository;
import com.booksphillic.repository.pickup.PickupRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.pickup.dto.PickupBookstoreDetailRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PickupService {

    private final PickupRepository pickupRepository;

    private final UserRepository userRepository;

    private final BookstoreRepository bookstoreRepository;

    @Transactional
    public Void postPickup(Long userId, Long storeId, String genre, LocalDateTime creationAt, String status, String requirements) throws BaseException {
        try {
            User user = checkUserId(userId);
            if(user == null) {
                throw new BaseException(BaseResponseCode.INVALID_USER_ID);
            }
            Bookstore bookstore = bookstoreRepository.findById(storeId);
            if(bookstore == null) {
                throw new BaseException(BaseResponseCode.INVALID_BOOKSTOREID);
            }


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
