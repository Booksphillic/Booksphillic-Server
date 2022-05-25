package com.booksphillic.repository.pickup;

import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.bookstore.DistrictType;
import com.booksphillic.domain.pickup.Pickup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PickupRepository {

    private final EntityManager em;

    public Pickup findById(Long id) {
        try {
            return em.find(Pickup.class, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public Bookstore findByStoreId(Long id) {
        return em.find(Bookstore.class, id);
    }

    public List<Pickup> findByUserId(Long userId) {
        try {
            return em.createQuery("select p from Pickup p where p.userId=:userId", Pickup.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public List<Bookstore> findByDistrict(String district) {
        try {
            DistrictType districtType = DistrictType.valueOf(district);

            return em.createQuery("select b from Bookstore b where b.address.district=:district", Bookstore.class)
                    .setParameter("district", districtType)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
