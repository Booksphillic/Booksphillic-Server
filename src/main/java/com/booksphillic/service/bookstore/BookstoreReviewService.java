package com.booksphillic.service.bookstore;

import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.bookstore.BookstoreReview;
import com.booksphillic.domain.bookstore.BookstoreReviewImage;
import com.booksphillic.domain.bookstore.Emoticon;
import com.booksphillic.domain.user.User;
import com.booksphillic.domain.user.UserPickupReviewCount;
import com.booksphillic.repository.UserRepository;
import com.booksphillic.repository.bookstore.BookstoreRepository;
import com.booksphillic.repository.bookstore.BookstoreReviewJpaRepository;
import com.booksphillic.repository.bookstore.ReviewImageJpaRepository;
import com.booksphillic.repository.user.UserPRCountRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.awsS3.FileProcessService;
import com.booksphillic.service.bookstore.dto.StoreReviewListRes;
import com.booksphillic.service.bookstore.dto.StoreReviewRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookstoreReviewService {

    private final FileProcessService fileProcessService;
    private final BookstoreReviewJpaRepository bookstoreReviewJpaRepositoryRepository;
    private final BookstoreRepository bookstoreRepository;
    private final UserRepository userRepository;
    private final ReviewImageJpaRepository reviewImageJpaRepository;
    private final UserPRCountRepository countRepository;

    @Transactional
    public List<String> postReviewImages(List<MultipartFile> files) throws BaseException {
        try {
            //이미지들 버킷에 저장하고 urls 배열 return
            List<String> urls = new ArrayList<>();

            for(MultipartFile file : files) {
                String url = fileProcessService.uploadImage(file, "BOOKSTORE_REVIEW");
                urls.add(url);
            }
            for(String url : urls) {
                System.out.println("url = " + url);
            }
            return urls;
        } catch (IllegalArgumentException iae) {
            log.error(iae.getMessage());
            throw new BaseException(BaseResponseCode.IMAGE_UPLOAD_ERROR);
        }
    }

    public StoreReviewRes postReview(Long storeId, Long userId, String content, String emoticon, List<String> urls) throws BaseException {
        try {
            Bookstore bookstore = bookstoreRepository.findById(storeId);
            if(bookstore == null) {
                throw new BaseException(BaseResponseCode.INVALID_BOOKSTOREID);
            }
            User user = checkUserId(userId);
            if(user == null) {
                throw new BaseException(BaseResponseCode.INVALID_USER_ID);
            }
            // BookstoreReview 에 리뷰 먼저 등록 -> reviewId 받아와서
            BookstoreReview review = BookstoreReview.builder()
                    .user(user)
                    .store(bookstore)
                    .content(content)
                    .emoticon(checkEmoticon(emoticon))
                    .build();
            bookstoreReviewJpaRepositoryRepository.save(review);
            List<String> resultUrls = new ArrayList<>();
            // url 배열 돌면서 (reviewId, url) BookstoreReviewImage 에 저장
            for(String url : urls) {
                reviewImageJpaRepository.save(BookstoreReviewImage.builder()
                        .storeReview(review)
                        .url(url)
                        .build()
                );
                resultUrls.add(url);
            }

            //UserPickupReviewCount에 reviewCount+1, phillic+10 해주기
            UserPickupReviewCount count = countRepository.findByUserId(userId).get();
            count.setReviewCount(count.getReviewCount()+1);
            count.setPhillic(count.getPhillic()+10);
            countRepository.save(count);

            return StoreReviewRes.builder()
                    .reviewId(review.getId())
                    .username(user.getUsername())
                    .urls(resultUrls).build();
        } catch (IllegalArgumentException iae) {
            log.error(iae.getMessage());
            throw new BaseException(BaseResponseCode.INVALID_USER_ID);
        }
        catch (BaseException e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }

    public Emoticon checkEmoticon(String emoticon) {
        for(Emoticon e : Emoticon.values()) {
            if(emoticon.equals(e.getDescription()))
                return e;
        }
        return null;
    }

    public List<StoreReviewListRes> getReviews(Long storeId) throws BaseException{
        try {
            Bookstore bookstore = bookstoreRepository.findById(storeId);
            if(bookstore == null) {
                throw  new BaseException(BaseResponseCode.INVALID_BOOKSTOREID);
            }
            List<StoreReviewListRes> results = new ArrayList<>();
            // BookstoreReview에서 reivew들 가져오고
            List<BookstoreReview> reviews = bookstoreReviewJpaRepositoryRepository.findByStore(bookstore);
            for (BookstoreReview review : reviews) {
                //각 review의 이미지 url들 가져오고
                List<String> urls =
                        reviewImageJpaRepository.findByStoreReview(review).stream()
                                        .map(img -> img.getUrl())
                                        .collect(Collectors.toList());
                results.add(
                        StoreReviewListRes.builder()
                                .reviewId(review.getId())
                                .username(review.getUser().getUsername())
                                .content(review.getContent())
                                .emoticon(review.getEmoticon().getDescription())
                                .createdAt(review.getCreatedAt())
                                .urls(urls)
                                .build()
                );
            }
            return results;

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
}
