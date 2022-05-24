package com.booksphillic.repository.user;

import com.booksphillic.domain.user.Inquiry;
import com.booksphillic.domain.user.InquiryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InquiryRepository {

    private final EntityManager em;

    public List<Inquiry> findByOwnerId(Long ownerId, int offset, int size) {
        String jpql = "select i from Inquiry i where i.ownerId = :ownerId";
        return em.createQuery(jpql, Inquiry.class)
                .setParameter("ownerId", ownerId)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }


    public List<Inquiry> findByOwnerIdAndType(Long ownerId, InquiryType type, int offset, int size) {
        String jpql = "select i from Inquiry i where i.ownerId = :ownerId and i.type = :type";
        return em.createQuery(jpql, Inquiry.class)
                .setParameter("ownerId", ownerId)
                .setParameter("type", type)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

}
