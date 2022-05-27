package com.booksphillic.service.tag;

import com.booksphillic.domain.bookstore.Tag;
import com.booksphillic.repository.tag.BookstoreTagJpaRepository;
import com.booksphillic.repository.tag.BookstoreTagRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.tag.dto.GetTagStoresRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagService {

    private final BookstoreTagRepository bookstoreTagRepository;
    private final BookstoreTagJpaRepository bookstoreTagJpaRepository;

    public List<GetTagStoresRes> getTagStore(String name) throws BaseException{
        try{
            return bookstoreTagRepository.findStoreByName(name).stream().map(bt ->
                    GetTagStoresRes.builder()
                            .storeId(bt.getStore().getId())
                            .name(bt.getStore().getName())
                            .description(bt.getStore().getDescription())
                            .district(bt.getStore().getAddress().getDistrict().getEn())
                            .profileImg(bt.getStore().getProfileImgUrl())
                            .build()
            ).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }

    // 책방의 태그 리스트 조회
    public List<Tag> getStoreTags(Long storeId) throws BaseException {
        try{
            List<Tag> tags = bookstoreTagJpaRepository.findByStoreId(storeId)
                    .stream().map(bt -> bt.getTag()).collect(Collectors.toList());
            return tags;
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }



}
