package com.tu.javaproject.controller;

import com.tu.javaproject.service.RoomExtraService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/extras")
@RequiredArgsConstructor
public class RoomExtraController {

    private final RoomExtraService roomExtraService;
}
