
package com.tu.javaproject.controller;

import com.tu.javaproject.dto.HotelDto;
import com.tu.javaproject.service.HotelService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    @Operation(summary = "Get all hotels")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<HotelDto> findAll() {
        return hotelService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get hotel by ID")
    @ApiResponse(responseCode = "200", description = "Hotel found")
    @ApiResponse(responseCode = "404", description = "Hotel not found")
    public HotelDto findById(
            @Parameter(description = "Hotel ID")
            @PathVariable Long id) {
        return hotelService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create hotel")
    @ApiResponse(responseCode = "201", description = "Hotel created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public HotelDto create(
            @Valid @RequestBody HotelDto dto) {
        return hotelService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update hotel")
    @ApiResponse(responseCode = "200", description = "Hotel updated")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Hotel not found")
    public HotelDto update(
            @Parameter(description = "Hotel ID")
            @PathVariable Long id,
            @Valid @RequestBody HotelDto dto) {
        return hotelService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete hotel")
    @ApiResponse(responseCode = "204", description = "Hotel deleted")
    @ApiResponse(responseCode = "404", description = "Hotel not found")
    @ApiResponse(responseCode = "409", description = "Hotel has rooms")
    public void delete(
            @Parameter(description = "Hotel ID")
            @PathVariable Long id) {
        hotelService.delete(id);
    }
}
