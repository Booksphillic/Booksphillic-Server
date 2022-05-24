package com.booksphillic.service.user;


import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.bookstore.BookstoreImage;
import com.booksphillic.domain.bookstore.BookstoreTag;
import com.booksphillic.domain.user.User;
import com.booksphillic.repository.BookstoreTagRepository;
import com.booksphillic.repository.UserRepository;
import com.booksphillic.repository.bookstore.BookstoreRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.user.dto.GetOwnerProfileRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OwnerService {

    private final UserRepository userRepository;
    private final BookstoreRepository bookstoreRepository;
    private final BookstoreTagRepository tagRepository;

    public GetOwnerProfileRes getStoreProfile(Long userId) throws BaseException {
        try {
            System.out.println("userId = " + userId);
            User user = checkUserId(userId);
            if(user == null) {
                throw new BaseException(BaseResponseCode.INVALID_USER_ID);
            }
            Bookstore bookstore = bookstoreRepository.findByUserId(userId);

            List<BookstoreImage> internalImages = bookstoreRepository.findBookstoreImgsById(bookstore.getId());
            List<BookstoreTag> tags = tagRepository.findByStoreId(bookstore.getId());
            return GetOwnerProfileRes.builder()
                    .profileImgUrl(bookstore.getProfileImgUrl())
                    .name(bookstore.getName())
                    .subtitle(bookstore.getSubtitle())
                    .description(bookstore.getDescription())
                    .tags(tags.stream()
                            .map(t -> t.getTag().getName())
                            .collect(Collectors.toList()))
                    .address(bookstore.getAddress().toString())
                    .contact(bookstore.getContact())
                    .website(bookstore.getWebsite())
                    .hours(bookstore.getHours().toString())
                    .facility(bookstore.getFacility())
                    .internalImages(internalImages.stream()
                                    .map(i -> i.getUrl()).collect(Collectors.toList()))
                    .build();

        } catch (IllegalArgumentException iae) {
            log.error(iae.getMessage());
            throw new BaseException(BaseResponseCode.INVALID_USER_ID);
        } catch (NoResultException ne) {
            log.error(ne.getMessage());
            throw new BaseException(BaseResponseCode.UNAUTHORIZED_USER); // 일반 회원은 사장님 마이페이지에 접근할 권한이 없음
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }

    private User checkUserId(Long userId) throws IllegalArgumentException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 ID"));
    }
}
