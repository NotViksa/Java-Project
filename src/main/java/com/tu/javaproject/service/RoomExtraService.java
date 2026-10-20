package com.tu.javaproject.service;

import com.tu.javaproject.repository.RoomExtraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomExtraService {

    private final RoomExtraRepository roomExtraRepository;
}
