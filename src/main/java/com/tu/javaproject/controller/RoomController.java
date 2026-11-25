
package com.tu.javaproject.controller;

import com.tu.javaproject.dto.RoomDto;
import com.tu.javaproject.service.RoomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    @Operation(summary = "Get all rooms")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<RoomDto> findAll(
            @Parameter(description = "Filter by hotel ID")
            @RequestParam(required = false) Long hotelId) {
        return roomService.findAll(hotelId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get room by ID")
    @ApiResponse(responseCode = "200", description = "Room found")
    @ApiResponse(responseCode = "404", description = "Room not found")
    public RoomDto findById(
            @Parameter(description = "Room ID")
            @PathVariable Long id) {
        return roomService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create room")
    @ApiResponse(responseCode = "201", description = "Room created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Hotel or extra not found")
    @ApiResponse(responseCode = "409", description = "Room number already exists")
    public RoomDto create(
            @Valid @RequestBody RoomDto dto) {
        return roomService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update room")
    @ApiResponse(responseCode = "200", description = "Room updated")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Room, hotel or extra not found")
    @ApiResponse(responseCode = "409", description = "Room number already exists")
    public RoomDto update(
            @Parameter(description = "Room ID")
            @PathVariable Long id,
            @Valid @RequestBody RoomDto dto) {
        return roomService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete room")
    @ApiResponse(responseCode = "204", description = "Room deleted")
    @ApiResponse(responseCode = "404", description = "Room not found")
    @ApiResponse(responseCode = "409", description = "Room has reservations")
    public void delete(
            @Parameter(description = "Room ID")
            @PathVariable Long id) {
        roomService.delete(id);
    }

    @GetMapping("/available")
    @Operation(summary = "Find available rooms")
    @ApiResponse(responseCode = "200", description = "Available rooms found")
    @ApiResponse(responseCode = "400", description = "Invalid dates or guest count")
    @ApiResponse(responseCode = "404", description = "Hotel not found")
    public List<RoomDto> findAvailable(

            @Parameter(description = "Hotel ID")
            @RequestParam Long hotelId,

            @Parameter(description = "Check-in date")
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate checkInDate,

            @Parameter(description = "Check-out date")
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate checkOutDate,

            @Parameter(description = "Number of guests")
            @RequestParam int guestCount) {

        return roomService.findAvailable(
                hotelId,
                checkInDate,
                checkOutDate,
                guestCount);
    }
}
