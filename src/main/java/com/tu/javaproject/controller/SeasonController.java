
package com.tu.javaproject.controller;

import com.tu.javaproject.dto.SeasonDto;
import com.tu.javaproject.service.SeasonService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/seasons")
@RequiredArgsConstructor
public class SeasonController {

    private final SeasonService seasonService;

    @GetMapping
    @Operation(summary = "Get all seasons")
    @ApiResponse(responseCode = "200", description = "Success")
    public List<SeasonDto> findAll() {
        return seasonService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get season by ID")
    @ApiResponse(responseCode = "200", description = "Season found")
    @ApiResponse(responseCode = "404", description = "Season not found")
    public SeasonDto findById(
            @Parameter(description = "Season ID")
            @PathVariable Long id) {

        return seasonService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create season")
    @ApiResponse(responseCode = "201", description = "Season created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "409", description = "Overlapping seasons")
    public SeasonDto create(
            @Valid @RequestBody SeasonDto dto) {

        return seasonService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update season")
    @ApiResponse(responseCode = "200", description = "Season updated")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Season not found")
    @ApiResponse(responseCode = "409", description = "Overlapping seasons")
    public SeasonDto update(
            @Parameter(description = "Season ID")
            @PathVariable Long id,
            @Valid @RequestBody SeasonDto dto) {

        return seasonService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete season")
    @ApiResponse(responseCode = "204", description = "Season deleted")
    @ApiResponse(responseCode = "404", description = "Season not found")
    public void delete(
            @Parameter(description = "Season ID")
            @PathVariable Long id) {

        seasonService.delete(id);
    }
}
