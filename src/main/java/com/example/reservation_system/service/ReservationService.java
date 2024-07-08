package com.example.reservation_system.service;

import com.example.reservation_system.entity.Reservation;
import com.example.reservation_system.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    // 예약 등록
    public Reservation createReservation(Reservation reservation) {
        // 예약하려는 시간에 예약이 있는지 확인 후 예약
        if (isReservationTimeAvailable(reservation.getStore().getId(), reservation.getReservationTime())) {
            return reservationRepository.save(reservation);
        } else {
            throw new RuntimeException("Reservation time is not available");
        }
    }

    // 예약 수정
    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id " + id));
        reservation.setReservationTime(reservationDetails.getReservationTime());
        reservation.setStatus(reservationDetails.getStatus());
        return reservationRepository.save(reservation);
    }

    // 예약 취소
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id " + id));
        reservationRepository.delete(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // 예약 조회
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id " + id));
    }

    public boolean isReservationTimeAvailable(Long storeId, LocalDateTime reservationTime) {
        List<Reservation> existingReservations = reservationRepository.findByStoreIdAndReservationTime(storeId, reservationTime);
        return existingReservations.isEmpty();
    }

    // 10분전에 도착하여 도착확인하는 기능
    public boolean checkIn (Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id " + id));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservationTime = reservation.getReservationTime();

        // 예약시간 10분전부터 예약시간까지 도착확인 가능함
        if (now.isAfter(reservationTime.minusMinutes(10)) && now.isBefore(reservationTime)) {
            reservation.setStatus("checked-in");
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    // 이용 완료 ( 에약 이용 후 리뷰작성을 위한 )
    public boolean completeReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id " + id));

        // 예약확정이 되어있는 예약이면 이용완료로 상태변경
        if ("checked-in".equals(reservation.getStatus())) {
            reservation.setStatus("completed");
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }
}
