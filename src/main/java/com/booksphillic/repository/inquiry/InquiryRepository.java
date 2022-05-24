package com.booksphillic.repository.inquiry;

import com.booksphillic.domain.inquiry.Inquiry;
import com.booksphillic.domain.inquiry.InquiryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InquiryRepository {

    private final EntityManager em;

    public List<Inquiry> findByInquirerId(Long inquirerId, int offset, int size) {
        String jpql = "select i from Inquiry i where i.inquirerId = :inquirerId";
        return em.createQuery(jpql, Inquiry.class)
                .setParameter("inquirerId", inquirerId)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }


    public List<Inquiry> findByStoreIdAndType(Long storeId, InquiryType type, int offset, int size) {
        String jpql = "select i from Inquiry i where i.storeId = :storeId and i.type = :type";
        return em.createQuery(jpql, Inquiry.class)
                .setParameter("storeId", storeId)
                .setParameter("type", type)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

    public List<Inquiry> findPublicByStoreId(Long storeId, int offset, int size) {
        String jpql = "select i from Inquiry i where i.storeId = :storeId and i.isPublic = true";
        return em.createQuery(jpql, Inquiry.class)
                .setParameter("storeId", storeId)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }
}
