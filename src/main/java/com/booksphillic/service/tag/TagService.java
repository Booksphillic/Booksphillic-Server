package com.booksphillic.service.tag;

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



}
