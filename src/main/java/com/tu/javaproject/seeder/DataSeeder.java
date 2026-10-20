package com.tu.javaproject.seeder;

import com.tu.javaproject.repository.GuestRepository;
import com.tu.javaproject.repository.HotelRepository;
import com.tu.javaproject.repository.RoomExtraRepository;
import com.tu.javaproject.repository.RoomRepository;
import com.tu.javaproject.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final RoomExtraRepository roomExtraRepository;
    private final SeasonRepository seasonRepository;
    private final GuestRepository guestRepository;

    @Override
    public void run(String... args) {
    }
}
