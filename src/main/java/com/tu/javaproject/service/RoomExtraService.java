
package com.tu.javaproject.service;

import com.tu.javaproject.dto.RoomExtraDto;
import com.tu.javaproject.mapper.RoomExtraMapper;
import com.tu.javaproject.model.RoomExtra;
import com.tu.javaproject.repository.RoomExtraRepository;
import com.tu.javaproject.exception.NotFoundException;
import com.tu.javaproject.exception.ConflictException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomExtraService {

    private final RoomExtraRepository roomExtraRepository;
    private final RoomExtraMapper roomExtraMapper;

    @Transactional(readOnly = true)
    public List<RoomExtraDto> findAll() {
        return roomExtraRepository.findAll()
                .stream()
                .map(roomExtraMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoomExtraDto findById(Long id) {
        return roomExtraMapper.toDto(getExtra(id));
    }

    public RoomExtraDto create(RoomExtraDto dto) {

        if (roomExtraRepository.findByName(dto.name()).isPresent()) {
            throw new ConflictException("Extra already exists");
        }

        RoomExtra extra = roomExtraMapper.toEntity(dto);

        return roomExtraMapper.toDto(
                roomExtraRepository.save(extra));
    }

    public RoomExtraDto update(Long id, RoomExtraDto dto) {

        RoomExtra extra = getExtra(id);

        roomExtraRepository.findByName(dto.name())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new ConflictException("Extra already exists");
                });

        extra.setName(dto.name());

        return roomExtraMapper.toDto(
                roomExtraRepository.save(extra));
    }

    public void delete(Long id) {

        RoomExtra extra = getExtra(id);

        if (!extra.getRooms().isEmpty()) {
            throw new ConflictException(
                    "Extra is used by rooms and cannot be deleted");
        }

        roomExtraRepository.delete(extra);
    }

    private RoomExtra getExtra(Long id) {
        return roomExtraRepository.findById(id)
                .orElseThrow(() ->
                    new NotFoundException("Extra not found"));
    }
}
