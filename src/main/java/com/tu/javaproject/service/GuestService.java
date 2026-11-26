
package com.tu.javaproject.service;

import com.tu.javaproject.dto.GuestDto;
import com.tu.javaproject.mapper.GuestMapper;
import com.tu.javaproject.model.Guest;
import com.tu.javaproject.repository.GuestRepository;
import com.tu.javaproject.repository.ReservationRepository;
import com.tu.javaproject.exception.NotFoundException;
import com.tu.javaproject.exception.ConflictException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GuestService {

    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;
    private final GuestMapper guestMapper;

    @Transactional(readOnly = true)
    public List<GuestDto> findAll() {
        return guestRepository.findAll()
                .stream()
                .map(guestMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public GuestDto findById(Long id) {
        return guestMapper.toDto(getGuest(id));
    }

    public GuestDto create(GuestDto dto) {

        if (guestRepository.existsByEmail(dto.email())) {
            throw new ConflictException("Email already exists");
        }

        Guest guest = guestMapper.toEntity(dto);

        return guestMapper.toDto(
                guestRepository.save(guest));
    }

    public GuestDto update(Long id, GuestDto dto) {

        Guest guest = getGuest(id);

        guestRepository.findByEmail(dto.email())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new ConflictException("Email already exists");
                });

        guest.setFirstName(dto.firstName());
        guest.setLastName(dto.lastName());
        guest.setEmail(dto.email());
        guest.setPhoneNumber(dto.phoneNumber());

        return guestMapper.toDto(
                guestRepository.save(guest));
    }

    public void delete(Long id) {

        Guest guest = getGuest(id);

        if (reservationRepository.existsByGuestId(id)) {
            throw new ConflictException(
                    "Guest has reservations and cannot be deleted");
        }

        guestRepository.delete(guest);
    }

    private Guest getGuest(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() ->
                    new NotFoundException("Guest not found"));
    }
}
