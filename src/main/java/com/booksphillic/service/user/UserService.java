package com.booksphillic.service.user;

import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.bookstore.BookstoreTag;
import com.booksphillic.domain.user.Scrap;
import com.booksphillic.domain.user.User;
import com.booksphillic.domain.user.UserPickupReviewCount;
import com.booksphillic.repository.BookstoreTagRepository;
import com.booksphillic.repository.UserRepository;
import com.booksphillic.repository.bookstore.BookstoreRepository;
import com.booksphillic.repository.user.ScrapRepository;
import com.booksphillic.repository.user.UserPRCountRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.awsS3.FileProcessService;
import com.booksphillic.service.user.dto.CountPickupReviewRes;
import com.booksphillic.service.user.dto.GetProfileRes;
import com.booksphillic.service.user.dto.GetScrapRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final ScrapRepository scrapRepository;
    private final BookstoreRepository bookstoreRepository;
    private final BookstoreTagRepository tagRepository;
    private final UserRepository userRepository;
    private final FileProcessService fileProcessService;
    private final UserPRCountRepository countRepository;


    public void postScrap(Long storeId, Long userId) throws BaseException {
        try {
            Bookstore bookstore = bookstoreRepository.findById(storeId);
            if(bookstore == null) {
                System.out.println("이거 널!!");
                throw new BaseException(BaseResponseCode.INVALID_BOOKSTOREID);
            }
            User user = checkUserId(userId);
            Scrap savedScrap = scrapRepository.save(Scrap.builder()
                    .userId(userId)
                    .storeId(storeId)
                    .build());

            if(savedScrap == null) throw new BaseException(BaseResponseCode.SCRAP_ERROR); //스크랩 실패
        }
        catch (NullPointerException e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.INVALID_BOOKSTOREID);
        }
        catch (IllegalArgumentException iae) {
            log.error(iae.getMessage());
            throw new BaseException(BaseResponseCode.INVALID_USER_ID);
        }
    }

    // 성능 고려해보기!!!
    public List<GetScrapRes> getScrap(Long userId) throws BaseException {
        try {
            List<Scrap> scraps = scrapRepository.findByUserId(userId);
            List<GetScrapRes> results = new ArrayList<>();
            for(Scrap scrap : scraps) {
                Bookstore bookstore = bookstoreRepository.findById(scrap.getStoreId());
                List<BookstoreTag> storeTags = tagRepository.findByStoreId(scrap.getStoreId());
                //서점 이름 , 서점 소제목 , 서점 태그 리스트 , 서점 대표 이미지
                results.add(GetScrapRes.builder()
                        .scrapId(scrap.getId())
                        .bookstore(bookstore.getName())
                        .profileImgUrl(bookstore.getProfileImgUrl())
                        .tags(storeTags.stream()
                                .map(tag -> tag.getTag().getName())
                                .collect(Collectors.toList()))
                        .build());
            }
            return results;
        }
        catch(Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }

    public GetProfileRes getProfile(Long userId) throws BaseException  {
        try {
            User user = userRepository.findById(userId).get();
            if(user == null)
                throw new BaseException(BaseResponseCode.INVALID_USER_ID);
            return GetProfileRes.builder()
                    .profileImgUrl(user.getProfileImgUrl())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .build();
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }

    public String postProfileImage(MultipartFile file, Long userId) throws BaseException{
        try {
            // AWS S3에 업로드
            String url = fileProcessService.uploadImage(file, "USER_PROFILE");
            System.out.println("url = " + url);

            // DB에 업로드
            User user = userRepository.findById(userId).get();
            user.setProfileImgUrl(url);
            userRepository.save(user);
            return url;

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.IMAGE_UPLOAD_ERROR);
        }
    }

    public void deleteProfileImage(Long userId) throws BaseException {
        try {
            User user = userRepository.findById(userId).get();
            user.setProfileImgUrl(null);
            userRepository.save(user);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }

    public CountPickupReviewRes countPickupReview(Long userId) throws BaseException {
        try {
            User user = checkUserId(userId);
            UserPickupReviewCount userCount = countRepository.findByUserId(userId).get();
            return CountPickupReviewRes.builder()
                    .pickupCount(userCount.getPickupCount())
                    .reviewCount(userCount.getReviewCount())
                    .phillic(userCount.getPhillic())
                    .build();

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.INVALID_USER_ID);
        }
    }

    private User checkUserId(Long userId) throws IllegalArgumentException {
        User user = userRepository.findById(userId)
                .orElseThrow(IllegalArgumentException::new);

        return user;
    }
}
