package com.tu.javaproject.service;

import com.tu.javaproject.repository.GuestRepository;
import com.tu.javaproject.repository.ReservationRepository;
import com.tu.javaproject.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final PricingService pricingService;
}
