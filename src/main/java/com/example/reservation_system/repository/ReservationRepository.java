package com.example.reservation_system.repository;

import com.example.reservation_system.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByStoreIdAndReservationTime(Long storeId, LocalDateTime reservationTime);

}
