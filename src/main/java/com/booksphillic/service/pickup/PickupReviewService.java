package com.booksphillic.service.pickup;

import com.booksphillic.domain.bookstore.Emoticon;
import com.booksphillic.domain.pickup.Pickup;
import com.booksphillic.domain.pickup.PickupReview;
import com.booksphillic.domain.pickup.PickupReviewImage;
import com.booksphillic.domain.user.User;
import com.booksphillic.domain.user.UserPickupReviewCount;
import com.booksphillic.repository.UserRepository;
import com.booksphillic.repository.pickup.PickupRepository;
import com.booksphillic.repository.pickup.PickupReviewImageJpaRepository;
import com.booksphillic.repository.pickup.PickupReviewJpaRepository;
import com.booksphillic.repository.user.UserPRCountRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.awsS3.FileProcessService;
import com.booksphillic.service.pickup.dto.PickupReviewListRes;
import com.booksphillic.service.pickup.dto.PickupReviewRes;
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
public class PickupReviewService {

    private final FileProcessService fileProcessService;
    private final PickupRepository pickupRepository;
    private final PickupReviewJpaRepository pickupReviewJpaRepository;
    private final PickupReviewImageJpaRepository pickupReviewImageJpaRepository;
    private final UserRepository userRepository;
    private final UserPRCountRepository countRepository;

    @Transactional
    public List<String> postPickupReviewImages(List<MultipartFile> files) throws BaseException {
        try {
            List<String> urls = new ArrayList<>();

            for (MultipartFile file : files) {
                String url = fileProcessService.uploadImage(file, "MYSTERYTBOOK_REVIEW");
                urls.add(url);
            }

            return urls;
        } catch (IllegalArgumentException iae) {
            log.error(iae.getMessage());
            throw new BaseException(BaseResponseCode.IMAGE_UPLOAD_ERROR);
        }
    }

    public PickupReviewRes postReview(Long pickupId, Long userId, String content, String emoticon, List<String> urls) throws BaseException {
        try {
            Pickup pickup = pickupRepository.findById(pickupId);
            if (pickup == null) {
                throw new BaseException(BaseResponseCode.INVALID_PICKUP_ID);
            }

            User user = userRepository.findById(userId)
                    .orElseThrow(IllegalAccessError::new);
            if (user == null) {
                throw new BaseException(BaseResponseCode.INVALID_USER_ID);
            }

            PickupReview review = PickupReview.builder()
                    .user(user)
                    .pickup(pickup)
                    .content(content)
                    .emoticon(checkEmoticon(emoticon))
                    .build();

            pickupReviewJpaRepository.save(review);

            List<String> resultUrls = new ArrayList<>();

            for (String url : urls) {
                pickupReviewImageJpaRepository.save(PickupReviewImage.builder()
                        .pickupReview(review)
                        .url(url)
                        .build()
                );
                resultUrls.add(url);
            }

            UserPickupReviewCount count = countRepository.findByUserId(userId).get();
            count.setReviewCount(count.getPickupCount()+1);
            count.setPhillic(count.getPhillic()+10);
            countRepository.save(count);

            return PickupReviewRes.builder()
                    .reviewId(review.getId())
                    .username(user.getUsername())
                    .urls(resultUrls)
                    .content(review.getContent())
                    .emoticon(review.getEmoticon().getDescription())
                    .build();

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

    public List<PickupReviewListRes> getPickupReviews(Long pickupId) throws BaseException {
        try {
            Pickup pickup = pickupRepository.findById(pickupId);

            if (pickup == null) {
                throw new BaseException(BaseResponseCode.INVALID_PICKUP_ID);
            }

            List<PickupReviewListRes> results = new ArrayList<>();
            List<PickupReview> reviews = pickupReviewJpaRepository.findByPickup(pickup);

            for (PickupReview review : reviews) {
                List<String> urls = pickupReviewImageJpaRepository.findByPickupReview(review).stream()
                        .map(img -> img.getUrl())
                        .collect(Collectors.toList());

                results.add(
                        PickupReviewListRes.builder()
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
}
