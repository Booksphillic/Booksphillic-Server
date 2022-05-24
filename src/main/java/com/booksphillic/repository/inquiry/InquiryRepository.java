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

    public List<Inquiry> findByUserId(Long userId, int offset, int size) {
        String jpql = "select i from Inquiry i where i.ownerId = :userId or i.inquirerId = :userId";
        return em.createQuery(jpql, Inquiry.class)
                .setParameter("userId", userId)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }


    public List<Inquiry> findByUserIdAndType(Long userId, InquiryType type, int offset, int size) {
        String jpql = "select i from Inquiry i where (i.ownerId = :userId or i.inquirerId = :userId) and i.type = :type";
        return em.createQuery(jpql, Inquiry.class)
                .setParameter("userId", userId)
                .setParameter("type", type)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

    public List<Inquiry> findPublicByUserId(Long userId, int offset, int size) {
        String jpql = "select i from Inquiry i where (i.ownerId = :userId or i.inquirerId = :userId) and i.isPublic = true";
        return em.createQuery(jpql, Inquiry.class)
                .setParameter("userId", userId)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

    public List<Inquiry> findPublicByUserIdAndType(Long userId, InquiryType type, int offset, int size) {
        String jpql = "select i from Inquiry i where (i.ownerId = :userId or i.inquirerId = :userId) and i.type = :type and i.isPublic = true";
        return em.createQuery(jpql, Inquiry.class)
                .setParameter("userId", userId)
                .setParameter("type", type)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }
}
