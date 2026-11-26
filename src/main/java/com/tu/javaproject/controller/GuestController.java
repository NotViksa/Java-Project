
package com.tu.javaproject.controller;

import com.tu.javaproject.dto.GuestDto;
import com.tu.javaproject.service.GuestService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @GetMapping
    @Operation(summary = "Get all guests")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<GuestDto> findAll() {
        return guestService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get guest by ID")
    @ApiResponse(responseCode = "200", description = "Guest found")
    @ApiResponse(responseCode = "404", description = "Guest not found")
    public GuestDto findById(
            @Parameter(description = "Guest ID")
            @PathVariable Long id) {
        return guestService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create guest")
    @ApiResponse(responseCode = "201", description = "Guest created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "409", description = "Email already exists")
    public GuestDto create(
            @Valid @RequestBody GuestDto dto) {
        return guestService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update guest")
    @ApiResponse(responseCode = "200", description = "Guest updated")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Guest not found")
    @ApiResponse(responseCode = "409", description = "Email already exists")
    public GuestDto update(
            @Parameter(description = "Guest ID")
            @PathVariable Long id,
            @Valid @RequestBody GuestDto dto) {
        return guestService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete guest")
    @ApiResponse(responseCode = "204", description = "Guest deleted")
    @ApiResponse(responseCode = "404", description = "Guest not found")
    @ApiResponse(responseCode = "409", description = "Guest has reservations")
    public void delete(
            @Parameter(description = "Guest ID")
            @PathVariable Long id) {
        guestService.delete(id);
    }
}
