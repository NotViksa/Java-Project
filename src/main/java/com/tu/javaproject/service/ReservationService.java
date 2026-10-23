package com.tu.javaproject.service;

import com.tu.javaproject.exception.ConflictException;
import com.tu.javaproject.exception.NotFoundException;
import com.tu.javaproject.model.Reservation;
import com.tu.javaproject.model.ReservationStatus;
import com.tu.javaproject.repository.GuestRepository;
import com.tu.javaproject.repository.ReservationRepository;
import com.tu.javaproject.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final PricingService pricingService;

    @Transactional(readOnly = true)
    public List<Reservation> getAll() {
        return reservationRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public List<Reservation> getByGuestId(Long guestId) {
        return reservationRepository.findByGuestId(guestId);
    }

    @Transactional(readOnly = true)
    public Reservation getById(Long id) {
        return reservationRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new NotFoundException("Reservation " + id + " not found"));
    }

    @Transactional
    public Reservation cancel(Long id) {
        Reservation reservation = findForUpdate(id);
        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new ConflictException("Only confirmed reservations can be cancelled");
        }
        if (!LocalDate.now().isBefore(reservation.getCheckInDate())) {
            throw new ConflictException("Reservation can only be cancelled before check-in");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        return reservation;
    }

    @Transactional
    public Reservation checkIn(Long id) {
        Reservation reservation = findForUpdate(id);
        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new ConflictException("Only confirmed reservations can be checked in");
        }
        if (LocalDate.now().isBefore(reservation.getCheckInDate())) {
            throw new ConflictException("Check-in is not available before " + reservation.getCheckInDate());
        }

        reservation.setStatus(ReservationStatus.CHECKED_IN);
        return reservation;
    }

    @Transactional
    public Reservation checkOut(Long id) {
        Reservation reservation = findForUpdate(id);
        if (reservation.getStatus() != ReservationStatus.CHECKED_IN) {
            throw new ConflictException("Only checked-in reservations can be checked out");
        }
        if (!LocalDate.now().isAfter(reservation.getCheckInDate())) {
            throw new ConflictException("Check-out is only available after check-in date");
        }

        reservation.setStatus(ReservationStatus.CHECKED_OUT);
        return reservation;
    }

    private Reservation findForUpdate(Long id) {
        return reservationRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new NotFoundException("Reservation " + id + " not found"));
    }
}
