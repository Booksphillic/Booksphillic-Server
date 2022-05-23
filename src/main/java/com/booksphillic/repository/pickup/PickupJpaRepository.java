package com.booksphillic.repository.pickup;

import com.booksphillic.domain.pickup.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickupJpaRepository extends JpaRepository<Pickup, Long> {
}
