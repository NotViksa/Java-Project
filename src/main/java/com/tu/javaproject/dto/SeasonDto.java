
package com.tu.javaproject.dto;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.math.BigDecimal;

public record SeasonDto(

    @Schema(description = "Season ID")
    Long id,

    @NotBlank
    @Size(max = 50)
    @Schema(description = "Season name")
    String name,

    @NotNull
    @Schema(description = "Start date")
    LocalDate startDate,

    @NotNull
    @Schema(description = "End date")
    LocalDate endDate,

    @NotNull
    @Positive
    @Digits(integer = 3, fraction = 2)
    @Schema(description = "Price multiplier")
    BigDecimal priceMultiplier

) {

    @JsonIgnore
    @AssertTrue(message = "End date must not be before start date")
    @Schema(hidden = true)
    public boolean isDateRangeValid() {
        return startDate == null
            || endDate == null
            || !endDate.isBefore(startDate);
    }
}
