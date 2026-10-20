package com.tu.javaproject.service;

import com.tu.javaproject.repository.HotelRepository;
import com.tu.javaproject.repository.RoomExtraRepository;
import com.tu.javaproject.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomExtraRepository roomExtraRepository;
}
