package com.booksphillic.repository.bookstore;

import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.bookstore.BookstoreImage;
import com.booksphillic.domain.bookstore.DistrictType;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BookstoreRepository {

    private final EntityManager em;

    /**
     * 서점 프로필 전체 조회
     */
    public List<Bookstore> findAll() {
        return em.createQuery("select b from Bookstore b", Bookstore.class)
                .getResultList();
    }

    public List<Bookstore> findByDistrict(String district) {
        try{
            System.out.println("District 검색 district = " + district );
            DistrictType districtType = DistrictType.valueOf(district);
            return em.createQuery("select b from Bookstore b where b.address.district=:district", Bookstore.class)
                    .setParameter("district", districtType)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 서점 프로필 세부 조회
     */
    public Bookstore findById(Long id) {
        try {
            return em.find(Bookstore.class, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public List<BookstoreImage> findBookstoreImgsById(Long id) {
        try {
            return em.createQuery("select bi from BookstoreImage bi where bi.bookstore.id = :id")
                    .setParameter("id", id)
                    .getResultList();
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public Bookstore findByUserId(Long userId) throws Exception {
        try {
            return (Bookstore) em.createQuery("select b from Bookstore b where b.user.id = :user")
                    .setParameter("user", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new NoResultException("해당 유저의 서점이 존재하지 않습니다.");
        }
    }
}
