
package com.tu.javaproject.controller;

import com.tu.javaproject.dto.RoomExtraDto;
import com.tu.javaproject.service.RoomExtraService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/extras")
@RequiredArgsConstructor
public class RoomExtraController {

    private final RoomExtraService roomExtraService;

    @GetMapping
    @Operation(summary = "Get all extras")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<RoomExtraDto> findAll() {
        return roomExtraService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get extra by ID")
    @ApiResponse(responseCode = "200", description = "Extra found")
    @ApiResponse(responseCode = "404", description = "Extra not found")
    public RoomExtraDto findById(
            @Parameter(description = "Extra ID")
            @PathVariable Long id) {
        return roomExtraService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create extra")
    @ApiResponse(responseCode = "201", description = "Extra created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "409", description = "Extra already exists")
    public RoomExtraDto create(
            @Valid @RequestBody RoomExtraDto dto) {
        return roomExtraService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update extra")
    @ApiResponse(responseCode = "200", description = "Extra updated")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Extra not found")
    @ApiResponse(responseCode = "409", description = "Extra already exists")
    public RoomExtraDto update(
            @Parameter(description = "Extra ID")
            @PathVariable Long id,
            @Valid @RequestBody RoomExtraDto dto) {
        return roomExtraService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete extra")
    @ApiResponse(responseCode = "204", description = "Extra deleted")
    @ApiResponse(responseCode = "404", description = "Extra not found")
    @ApiResponse(responseCode = "409", description = "Extra is used by rooms")
    public void delete(
            @Parameter(description = "Extra ID")
            @PathVariable Long id) {
        roomExtraService.delete(id);
    }
}
