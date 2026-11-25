
package com.tu.javaproject.service;

import com.tu.javaproject.dto.SeasonDto;
import com.tu.javaproject.mapper.SeasonMapper;
import com.tu.javaproject.model.Season;
import com.tu.javaproject.repository.SeasonRepository;
import com.tu.javaproject.exception.NotFoundException;
import com.tu.javaproject.exception.ConflictException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SeasonService {

    private final SeasonRepository seasonRepository;
    private final SeasonMapper seasonMapper;

    @Transactional(readOnly = true)
    public List<SeasonDto> findAll() {
        return seasonRepository.findAll()
                .stream()
                .map(seasonMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public SeasonDto findById(Long id) {
        return seasonMapper.toDto(getSeason(id));
    }

    public SeasonDto create(SeasonDto dto) {

        validateOverlap(
                dto.startDate(),
                dto.endDate(),
                null);

        Season season = seasonMapper.toEntity(dto);

        return seasonMapper.toDto(
                seasonRepository.save(season));
    }

    public SeasonDto update(Long id, SeasonDto dto) {

        Season season = getSeason(id);

        validateOverlap(
                dto.startDate(),
                dto.endDate(),
                id);

        season.setName(dto.name());
        season.setStartDate(dto.startDate());
        season.setEndDate(dto.endDate());
        season.setPriceMultiplier(dto.priceMultiplier());

        return seasonMapper.toDto(
                seasonRepository.save(season));
    }

    public void delete(Long id) {
        Season season = getSeason(id);
        seasonRepository.delete(season);
    }

    private Season getSeason(Long id) {
        return seasonRepository.findById(id)
                .orElseThrow(() ->
                    new NotFoundException("Season not found"));
    }

    private void validateOverlap(
            LocalDate startDate,
            LocalDate endDate,
            Long excludedId) {

        for (Season season : seasonRepository.findAll()) {

            if (season.getId().equals(excludedId)) {
                continue;
            }

            boolean overlaps =
                    !startDate.isAfter(season.getEndDate())
                    && !endDate.isBefore(season.getStartDate());

            if (overlaps) {
                throw new ConflictException(
                        "Season dates overlap");
            }
        }
    }
}
