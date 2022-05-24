package com.booksphillic.service.inquiry;

import com.booksphillic.domain.inquiry.Inquiry;
import com.booksphillic.domain.inquiry.InquiryType;
import com.booksphillic.repository.inquiry.InquiryRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import com.booksphillic.service.inquiry.dto.GetInquiryRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    // 문의내역 조회
    public List<GetInquiryRes> getInquiries(Long ownerId, boolean onlyPublic, int page, int size) throws BaseException {
        try{
            int offset = (page-1)*size;
            List<Inquiry> inquiries;
            if(onlyPublic) inquiries = inquiryRepository.findPublicByUserId(ownerId, offset, size);
            else inquiries = inquiryRepository.findByUserId(ownerId, offset, size);

            return inquiries.stream().map(GetInquiryRes::new)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }


    // 문의내역 타입별 조회
    public List<GetInquiryRes> getInquiriesByType(Long userId, boolean onlyPublic, InquiryType type, int page, int size) throws BaseException {
        try{
            int offset = (page-1)*size;
            List<Inquiry> inquiries;
            if(onlyPublic) inquiries = inquiryRepository.findPublicByUserIdAndType(userId, type, offset, size);
            else inquiries = inquiryRepository.findByUserIdAndType(userId, type, offset, size);

            return inquiries.stream().map(GetInquiryRes::new)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.DATABASE_ERROR);
        }
    }
}
