package com.example.reservation_system.controller;

import com.example.reservation_system.entity.Reservation;
import com.example.reservation_system.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // 예약 등록
    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationService.createReservation(reservation);
    }

    // 예약 수정
    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Long id, @RequestBody Reservation reservationDetails) {
        return reservationService.updateReservation(id, reservationDetails);
    }

    // 예약 취소
    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }

    // 모든 예약 조회
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    // id로 예약 조회
    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    // 예약시간 10분 전 도착 확인
    @PostMapping("/{id}/checkin")
    public String checkIn(@PathVariable Long id) {
        boolean checkInSuccessful = reservationService.checkIn(id);
        if (checkInSuccessful) {
            return "예약이 확정되었습니다.";
        } else {
            return "예약에 실패했습니다. : 예약 시간 10분전부터 확정이 가능합니다.";
        }
    }

    // 예약 이용 완료
    @PostMapping("/{id}/complete")
    public String completeReservation(@PathVariable Long id) {
        boolean completeSuccessful = reservationService.completeReservation(id);
        if (completeSuccessful) {
            return "이용이 완료되었습니다.";
        } else {
            return "예약 확정 후에 이용을 완료해 주세요.";
        }
    }
}
