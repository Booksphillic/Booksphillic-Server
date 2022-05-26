package com.booksphillic.service.bookstore;

import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.bookstore.BookstoreImage;
import com.booksphillic.domain.bookstore.BookstoreTag;
import com.booksphillic.domain.user.User;
import com.booksphillic.repository.tag.BookstoreTagJpaRepository;
import com.booksphillic.repository.tag.BookstoreTagRepository;
import com.booksphillic.repository.UserRepository;
import com.booksphillic.repository.bookstore.BookstoreRepository;
import com.booksphillic.repository.user.ScrapRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.bookstore.dto.BookstoreDetailRes;
import com.booksphillic.service.bookstore.dto.BookstoreListRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookstoreService {

    private final BookstoreRepository bookstoreRepository;
    private final BookstoreTagJpaRepository bookstoreTagJpaRepository;
    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<BookstoreListRes> findBookstoreList(Long userId, String district) throws BaseException {
        try {
            Optional<User> user =  userRepository.findById(userId);
            if(user.isEmpty()) {
                throw new BaseException(BaseResponseCode.INVALID_USER_ID);
            }
            List<Bookstore> savedData;
            if(district == "all") {
                savedData = bookstoreRepository.findAll();
            }
            else {
                savedData = bookstoreRepository.findByDistrict(district);
            }
            List<BookstoreListRes> result = new ArrayList<>();
            boolean isScraped;
            for(Bookstore bookstore : savedData) {
                isScraped = false;
                if( (scrapRepository.findByUserIdAndStoreId(userId, bookstore.getId())).isPresent()) {
                    isScraped = true;
                }

                result.add(
                        BookstoreListRes.builder()
                                .storeId(bookstore.getId())
                                .name(bookstore.getName())
                                .profileImgUrl(bookstore.getProfileImgUrl())
                                .subtitle(bookstore.getSubtitle())
                                .district(bookstore.getAddress().getDistrict().getKo().substring(0,2))
                                .isScraped(isScraped)
                                .build()
                );
            }
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }


    public BookstoreDetailRes findById(Long id, Long userId) throws BaseException {
        try {
            Bookstore bookstore = bookstoreRepository.findById(id);
            if(bookstore == null) {
                //존재하지 않는 id
                throw new BaseException(BaseResponseCode.INVALID_BOOKSTOREID);
            }
            Optional<User> user =  userRepository.findById(userId);
            if(user.isEmpty()) {
                throw new BaseException(BaseResponseCode.INVALID_USER_ID);
            }

            List<BookstoreImage> internalImages = bookstoreRepository.findBookstoreImgsById(id);
            List<BookstoreTag> tags = bookstoreTagJpaRepository.findByStoreId(id);
            boolean isScraped = false;
            if( (scrapRepository.findByUserIdAndStoreId(userId, bookstore.getId())).isPresent()) {
                isScraped = true;
            }
            return new BookstoreDetailRes(bookstore, internalImages, tags, isScraped);

        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }


}
