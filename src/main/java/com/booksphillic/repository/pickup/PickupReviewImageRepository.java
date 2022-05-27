package com.booksphillic.repository.pickup;

import com.booksphillic.domain.pickup.PickupReview;
import com.booksphillic.domain.pickup.PickupReviewImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PickupReviewImageRepository {

    private final EntityManager em;

    public List<PickupReviewImage> findByPickupReview(PickupReview pickupReview) {
        return em.createQuery("select ri from PickupReviewImage ri " +
                "where ri.pickupReview=:pickupReview")
                .setParameter("pickupReview", pickupReview)
                .setMaxResults(1)
                .getResultList();
    }
}
