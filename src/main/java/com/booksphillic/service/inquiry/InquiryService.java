package com.booksphillic.service.inquiry;

import com.booksphillic.domain.inquiry.Inquiry;
import com.booksphillic.domain.inquiry.InquiryType;
import com.booksphillic.repository.inquiry.InquiryJpaRepository;
import com.booksphillic.repository.inquiry.InquiryRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.inquiry.dto.GetInquiryRes;
import com.booksphillic.service.inquiry.dto.PostInquiryReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final InquiryJpaRepository inquiryJpaRepository;

    // 문의내역 조회
    public List<GetInquiryRes> getUserInquiries(Long userId, int page, int size) throws BaseException {
        try{
            int offset = (page-1)*size;
            List<Inquiry> inquiries = inquiryRepository.findByInquirerId(userId, offset, size);
            return inquiries.stream().map(GetInquiryRes::new)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }


    public List<GetInquiryRes> getStorePublicInquiries(Long storeId, int page, int size) throws BaseException {
        try{
            int offset = (page-1)*size;
            List<Inquiry> inquiries = inquiryRepository.findPublicByStoreId(storeId, offset, size);
            return inquiries.stream().map(GetInquiryRes::new)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }

    // 문의내역 타입별 조회
    public List<GetInquiryRes> getStoreInquiriesByType(Long storeId, InquiryType type, int page, int size) throws BaseException {
        try{
            int offset = (page-1)*size;
            List<Inquiry> inquiries = inquiryRepository.findByStoreIdAndType(storeId, type, offset, size);

            return inquiries.stream().map(GetInquiryRes::new)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }

    public Long createInquiry(PostInquiryReq postInquiryReq) throws BaseException {
        try{

            Inquiry savedInquiry = inquiryJpaRepository.save(new Inquiry(postInquiryReq));
            return savedInquiry.getInquiryId();

        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }
}
