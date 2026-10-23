package com.tu.javaproject.seeder;

import com.tu.javaproject.model.Guest;
import com.tu.javaproject.model.Hotel;
import com.tu.javaproject.model.Room;
import com.tu.javaproject.model.RoomExtra;
import com.tu.javaproject.model.RoomType;
import com.tu.javaproject.model.Season;
import com.tu.javaproject.repository.GuestRepository;
import com.tu.javaproject.repository.HotelRepository;
import com.tu.javaproject.repository.RoomExtraRepository;
import com.tu.javaproject.repository.RoomRepository;
import com.tu.javaproject.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final RoomExtraRepository roomExtraRepository;
    private final SeasonRepository seasonRepository;
    private final GuestRepository guestRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (hotelRepository.count() > 0) {
            log.info("Sample data already exists. Skipping seeding.");
            return;
        }

        RoomExtra wifi = roomExtraRepository.save(RoomExtra.builder().name("WiFi").build());
        RoomExtra tv = roomExtraRepository.save(RoomExtra.builder().name("TV").build());
        RoomExtra airConditioning = roomExtraRepository.save(RoomExtra.builder().name("Air conditioning").build());
        RoomExtra breakfast = roomExtraRepository.save(RoomExtra.builder().name("Breakfast").build());

        Hotel seaGarden = hotelRepository.save(Hotel.builder()
                .name("Sea Garden Hotel")
                .city("Varna")
                .address("Primorski Blvd 15")
                .description("Hotel near the sea garden and the beach.")
                .build());

        Hotel rila = hotelRepository.save(Hotel.builder()
                .name("Rila Hotel")
                .city("Sofia")
                .address("Tsar Osvoboditel Blvd 20")
                .description("City hotel in the center of Sofia.")
                .build());

        roomRepository.saveAll(List.of(
                room(seaGarden, "101", RoomType.SINGLE, 1, "80.00", wifi, tv, airConditioning),
                room(seaGarden, "102", RoomType.DOUBLE, 2, "120.00", wifi, tv, airConditioning),
                room(seaGarden, "201", RoomType.SUITE, 4, "220.00", wifi, tv, airConditioning, breakfast),
                room(rila, "101", RoomType.DOUBLE, 2, "110.00", wifi, tv),
                room(rila, "102", RoomType.FAMILY, 4, "180.00", wifi, tv, airConditioning, breakfast)
        ));

        seasonRepository.saveAll(List.of(
                Season.builder()
                        .name("Winter 2026")
                        .startDate(LocalDate.of(2026, 12, 1))
                        .endDate(LocalDate.of(2027, 2, 28))
                        .priceMultiplier(new BigDecimal("1.30"))
                        .build(),
                Season.builder()
                        .name("Summer 2027")
                        .startDate(LocalDate.of(2027, 6, 1))
                        .endDate(LocalDate.of(2027, 8, 31))
                        .priceMultiplier(new BigDecimal("1.50"))
                        .build()
        ));

        guestRepository.saveAll(List.of(
                Guest.builder()
                        .firstName("Ivan")
                        .lastName("Petrov")
                        .email("ivan.petrov@example.com")
                        .phoneNumber("+359888123456")
                        .build(),
                Guest.builder()
                        .firstName("Maria")
                        .lastName("Ivanova")
                        .email("maria.ivanova@example.com")
                        .phoneNumber("+359888654321")
                        .build()
        ));

        log.info("Sample data seeded.");
    }

    private Room room(Hotel hotel, String number, RoomType type, int capacity, String price, RoomExtra... extras) {
        return Room.builder()
                .roomNumber(number)
                .type(type)
                .capacity(capacity)
                .pricePerNight(new BigDecimal(price))
                .hotel(hotel)
                .extras(new HashSet<>(Set.of(extras)))
                .build();
    }
}
