
package com.tu.javaproject.service;

import com.tu.javaproject.model.Room;
import com.tu.javaproject.model.Season;
import com.tu.javaproject.repository.SeasonRepository;
import com.tu.javaproject.exception.BadRequestException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PricingService {

    private final SeasonRepository seasonRepository;

    public BigDecimal calculateTotal(
            Room room,
            LocalDate checkIn,
            LocalDate checkOut) {

        if (checkIn == null || checkOut == null
                || !checkOut.isAfter(checkIn)) {
            throw new BadRequestException("Invalid reservation dates");
        }

        List<Season> seasons = seasonRepository.findAll();

        BigDecimal total = BigDecimal.ZERO;

        for (LocalDate date = checkIn;
                date.isBefore(checkOut);
                date = date.plusDays(1)) {

            BigDecimal nightPrice = room.getPricePerNight();

            for (Season season : seasons) {

                if (!date.isBefore(season.getStartDate())
                        && !date.isAfter(season.getEndDate())) {

                    nightPrice = nightPrice.multiply(
                            season.getPriceMultiplier());

                    break;
                }
            }

            total = total.add(nightPrice);
        }

        long nights = ChronoUnit.DAYS.between(
                checkIn, checkOut);

        if (nights >= 7) {
            total = total.multiply(new BigDecimal("0.90"));
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
