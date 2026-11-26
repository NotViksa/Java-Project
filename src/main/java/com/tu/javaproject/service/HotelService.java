
package com.tu.javaproject.service;

import com.tu.javaproject.dto.HotelDto;
import com.tu.javaproject.mapper.HotelMapper;
import com.tu.javaproject.model.Hotel;
import com.tu.javaproject.repository.HotelRepository;
import com.tu.javaproject.repository.RoomRepository;
import com.tu.javaproject.exception.NotFoundException;
import com.tu.javaproject.exception.ConflictException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final HotelMapper hotelMapper;

    @Transactional(readOnly = true)
    public List<HotelDto> findAll() {
        return hotelRepository.findAll()
                .stream()
                .map(hotelMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public HotelDto findById(Long id) {
        return hotelMapper.toDto(getHotel(id));
    }

    public HotelDto create(HotelDto dto) {
        Hotel hotel = hotelMapper.toEntity(dto);
        return hotelMapper.toDto(
                hotelRepository.save(hotel));
    }

    public HotelDto update(Long id, HotelDto dto) {
        Hotel hotel = getHotel(id);

        hotel.setName(dto.name());
        hotel.setCity(dto.city());
        hotel.setAddress(dto.address());
        hotel.setDescription(dto.description());

        return hotelMapper.toDto(
                hotelRepository.save(hotel));
    }

    public void delete(Long id) {
        Hotel hotel = getHotel(id);

        if (roomRepository.existsByHotelId(id)) {
            throw new ConflictException(
                    "Hotel has rooms and cannot be deleted");
        }

        hotelRepository.delete(hotel);
    }

    private Hotel getHotel(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() ->
                    new NotFoundException("Hotel not found"));
    }
}
