package com.booksphillic.service.bookstore;

import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.bookstore.BookstoreImage;
import com.booksphillic.domain.bookstore.BookstoreTag;
import com.booksphillic.repository.BookstoreTagRepository;
import com.booksphillic.repository.bookstore.BookstoreRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.bookstore.dto.BookstoreDetailRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookstoreService {

    private final BookstoreRepository bookstoreRepository;
    private final BookstoreTagRepository bookstoreTagRepository;

    @Transactional
    public List<Bookstore> findAll() throws BaseException {
        try {
            return bookstoreRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }

    @Transactional
    public List<Bookstore> findByDistrict(String district) throws BaseException {
        try {
            return bookstoreRepository.findByDistrict(district);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }

    public BookstoreDetailRes findById(Long id) throws BaseException {
        try {
            Bookstore bookstore = bookstoreRepository.findById(id);
            if(bookstore == null) {
                //존재하지 않는 id
                throw new BaseException(BaseResponseCode.INVALID_BOOKSTOREID);
            }
            else {
                List<BookstoreImage> internalImages = bookstoreRepository.findBookstoreImgsById(id);
                List<BookstoreTag> tags = bookstoreTagRepository.findByStoreId(id);
                return new BookstoreDetailRes(bookstore, internalImages, tags);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }


}
