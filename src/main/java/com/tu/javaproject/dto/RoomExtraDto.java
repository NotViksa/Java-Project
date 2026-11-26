
package com.tu.javaproject.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public record RoomExtraDto(

    @Schema(description = "Extra ID")
    Long id,

    @NotBlank
    @Schema(description = "Extra name")
    String name

) {
}
