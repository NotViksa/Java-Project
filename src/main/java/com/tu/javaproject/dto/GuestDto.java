
package com.tu.javaproject.dto;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

public record GuestDto(

    @Schema(description = "Guest ID")
    Long id,

    @NotBlank
    @Schema(description = "First name")
    String firstName,

    @NotBlank
    @Schema(description = "Last name")
    String lastName,

    @NotBlank
    @Email
    @Schema(description = "Email address")
    String email,

    @NotBlank
    @Schema(description = "Phone number")
    String phoneNumber

) {
}
