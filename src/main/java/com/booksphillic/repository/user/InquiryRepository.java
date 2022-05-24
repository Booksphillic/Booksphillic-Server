package com.booksphillic.repository.user;

import com.booksphillic.domain.user.Inquiry;
import com.booksphillic.domain.user.InquiryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    List<Inquiry> findByOwnerIdAndType(Long ownerId, InquiryType type);
}
